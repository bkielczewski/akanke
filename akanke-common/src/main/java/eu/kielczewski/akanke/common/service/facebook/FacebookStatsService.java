package eu.kielczewski.akanke.common.service.facebook;

import eu.kielczewski.akanke.common.domain.FacebookStats;

import javax.validation.constraints.NotNull;

public interface FacebookStatsService {

    FacebookStats get(@NotNull String documentId);

}
