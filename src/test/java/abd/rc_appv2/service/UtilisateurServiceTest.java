// src/test/java/abd/rc_appv2/service/UtilisateurServiceTest.java
package abd.rc_appv2.service;

import abd.rc_appv2.model.Utilisateur;
import abd.rc_appv2.repository.UtilisateurRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UtilisateurServiceTest {

    @Autowired
    private UtilisateurService utilisateurService;   // le service que tu testes

    @MockBean
    private UtilisateurRepository utilisateurRepository; // mocké

    @Test
    void findByEmail_should_return_user_when_exists() {
        // Arrange
        Utilisateur mockUser = new Utilisateur();
        mockUser.setEmail("test@mail.com");
        when(utilisateurRepository.findByEmail("test@mail.com"))
                .thenReturn(Optional.of(mockUser)); // <- IMPORTANT si Optional

        // Act
        Utilisateur result = utilisateurService.findByEmail("test@mail.com");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo("test@mail.com");
    }
}
