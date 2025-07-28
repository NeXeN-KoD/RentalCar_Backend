package abd.rc_appv2.repository;

import abd.rc_appv2.model.Utilisateur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UtilisateurRepositoryTest {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Test
    public void testSaveUtilisateur() {
        Utilisateur u = new Utilisateur();
        u.setNom("Ali");
        u.setEmail("ali@mail.com");
        u.setMotDePasse("123");

        Utilisateur saved = utilisateurRepository.save(u);
        assertNotNull(saved.getId());
    }
}
