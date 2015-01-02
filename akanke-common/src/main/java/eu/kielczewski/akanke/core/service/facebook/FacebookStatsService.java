package eu.kielczewski.akanke.core.service.facebook;

import eu.kielczewski.akanke.core.domain.FacebookStats;

import javax.validation.constraints.NotNull;

public interface FacebookStatsService {

    FacebookStats get(@NotNull String documentId);

}
