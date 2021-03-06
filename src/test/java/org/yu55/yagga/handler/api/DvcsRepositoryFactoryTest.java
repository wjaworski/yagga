package org.yu55.yagga.handler.api;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.yu55.yagga.util.mockito.DvcsRepositoryResolverMockBehavior.should;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Test;

public class DvcsRepositoryFactoryTest {

    @Test
    public void shouldNotFactorizeRepositoryForUnsupportedDirectory() {
        // given
        Path unsupportedRepoPath = Paths.get("unsupported_repository");

        DvcsRepositoryResolver dvcsRepositoryResolver = should(DvcsRepositoryResolver.class)
                .notRecognizeRepository(unsupportedRepoPath)
                .get();

        DvcsRepositoryFactory factory = new DvcsRepositoryFactory(singletonList(dvcsRepositoryResolver));

        // when
        Optional<DvcsRepository> optionalRepository = factory.factorizeRepository(unsupportedRepoPath);

        // then
        assertThat(optionalRepository).isEmpty();
    }

    @Test
    public void shouldFactorizeKnownRepository() {
        // given
        Path repositoryPath = Paths.get("my_repository");
        DvcsRepository repository = mock(DvcsRepository.class);

        DvcsRepositoryResolver dvcsRepositoryResolver = should(DvcsRepositoryResolver.class)
                .supportDvcs()
                .recognizeRepository(repositoryPath)
                .resolveRepository(repositoryPath, repository)
                .get();

        DvcsRepositoryFactory factory = new DvcsRepositoryFactory(singletonList(dvcsRepositoryResolver));

        // when
        Optional<DvcsRepository> optionalRepository = factory.factorizeRepository(repositoryPath);

        // then
        assertThat(optionalRepository).contains(repository);
    }

}
