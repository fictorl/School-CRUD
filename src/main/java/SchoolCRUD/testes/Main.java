package SchoolCRUD.testes;

import SchoolCRUD.dao.AlunoDao;
import SchoolCRUD.modelo.Aluno;
import SchoolCRUD.util.JPAUtil;
import jakarta.persistence.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        EntityManager em = JPAUtil.getEntityManager();
        AlunoDao alunoDao = new AlunoDao(em);
        Aluno a1 = new Aluno("Ariadne", "SC3034437", "a@gmail.com", new BigDecimal(10), new BigDecimal(9), new BigDecimal(10));


        em.getTransaction().begin();
        alunoDao.cadastrar(a1);
        em.getTransaction().commit();
        em.close();
    }
}