package eu.kielczewski.akanke.core.dao.directory;

import com.google.common.base.MoreObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import static java.nio.file.StandardWatchEventKinds.*;

@Component
public class DirectoryWatcherDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryWatcherDao.class);
    private final ApplicationEventPublisher applicationEventPublisher;

    @Inject
    public DirectoryWatcherDao(final ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Async
    @SuppressWarnings("SameParameterValue")
    public void watch(String baseDir) throws IOException {
        LOGGER.debug("Watching directory={} for changes", baseDir);
        WatchService watchService = FileSystems.getDefault().newWatchService();
        registerAll(Paths.get(baseDir), watchService);
        WatchKey watchKey;
        try {
            //noinspection InfiniteLoopStatement
            for (; ; ) {
                watchKey = watchService.take();
                Path parent = (Path) watchKey.watchable();
                processWatchEvents(parent, watchKey.pollEvents(), watchService);
                watchKey.reset();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Watching directory={} was interrupted", baseDir, e);
        } catch (Exception e) {
            LOGGER.error("Unexpected exception while watching directory={}", baseDir, e);
        }
    }

    private void register(Path dir, WatchService watchService) throws IOException {
        LOGGER.trace("Registering directory={} to watch for changes", dir);
        dir.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
    }

    private void registerAll(Path start, final WatchService watchService) throws IOException {
        try {
            Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(final Path dir,
                                                         final BasicFileAttributes attrs) throws IOException {
                    if (!isIgnoredPath(dir.toString())) {
                        register(dir, watchService);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (NoSuchFileException e) {
            LOGGER.debug("Path does not exist anymore, ignoring", e);
        }
    }

    private boolean isIgnoredPath(String dir) {
        return dir.startsWith(".");
    }

    private void processWatchEvents(Path parent, List<WatchEvent<?>> events,
                                    WatchService watchService) throws IOException {
        for (WatchEvent<?> event : events) {
            Path child = parent.resolve((Path) event.context());
            if (event.kind() == ENTRY_CREATE && Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
                LOGGER.trace("Detected creation of the new directory={}, will watch it for changes", child);
                registerAll(child, watchService);
            }
            String target = child.toString();
            LOGGER.trace("Processing events for target={}, watchEventKind={}", target, event.kind());
            applicationEventPublisher.publishEvent(new FilesystemChangeEvent(this, target, watchEventKindToType(event.kind())));
        }
    }

    private FilesystemChangeEvent.Type watchEventKindToType(WatchEvent.Kind<?> kind) {
        if (kind == ENTRY_CREATE) {
            return FilesystemChangeEvent.Type.CREATE;
        }
        if (kind == ENTRY_MODIFY) {
            return FilesystemChangeEvent.Type.MODIFY;
        }
        if (kind == ENTRY_DELETE) {
            return FilesystemChangeEvent.Type.DELETE;
        }
        throw new RuntimeException(String.format("Unknown watch event of kind=%s", kind));
    }

    public static class FilesystemChangeEvent extends ApplicationEvent {

        private final String target;
        private final Type type;

        public FilesystemChangeEvent(Object source, String target, Type type) {
            super(source);
            this.target = target;
            this.type = type;
        }

        public String getTarget() {
            return target;
        }

        public Type getType() {
            return type;
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("target", target)
                    .add("type", type)
                    .toString();
        }

        public static enum Type {
            CREATE, DELETE, MODIFY
        }
    }

}
