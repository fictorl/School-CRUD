package SchoolCRUD.testes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import SchoolCRUD.dao.AlunoDao;
import SchoolCRUD.modelo.Aluno;
import SchoolCRUD.util.JPAUtil;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("School-CRUD");

    private static final EntityManager entityManager = entityManagerFactory.createEntityManager();

    public static void chamarMenu() {
        boolean continuar = true;

        while (continuar) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarAluno();
                    break;
                case 2:
                    excluirAluno();
                    break;
                case 3:
                    alterarAluno();
                    break;
                case 4:
                    buscarAlunoPeloNome();
                    break;
                case 5:
                    listarAlunos();
                    break;
                case 6:
                    System.out.println("FIM.");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida.Tente novamente.");
            }
        }
        scanner.close();
        entityManager.close();
        entityManagerFactory.close();
    }

    private static void exibirMenu() {
        System.out.println("**CADASTRO DE ALUNOS**");
        System.out.println("1 - Cadastrar aluno");
        System.out.println("2 - Excluir aluno");
        System.out.println("3 - Alterar aluno");
        System.out.println("4 - Buscar aluno pelo nome");
        System.out.println("5 - Listar alunos");
        System.out.println("6 - FIM");
        System.out.print("Digite a opção desejada: ");
    }

    private static void cadastrarAluno() {
        EntityManager em = JPAUtil.getEntityManager();

        System.out.println("Digite o nome do aluno: ");
        String nome = scanner.nextLine();
        System.out.println("Digite o RA do aluno: ");
        String ra = scanner.nextLine();
        System.out.println("Digite o e-mail do aluno: ");
        String email = scanner.nextLine();
        System.out.println("Digite a nota 1 do aluno: ");
        BigDecimal nota1 = scanner.nextBigDecimal();
        System.out.println("Digite a nota 2 do aluno: ");
        BigDecimal nota2 = scanner.nextBigDecimal();
        System.out.println("Digite a nota 3 do aluno: ");
        BigDecimal nota3 = scanner.nextBigDecimal();

        AlunoDao alunoDao = new AlunoDao(em);
        Aluno novoAluno = new Aluno(nome, ra, email, nota1, nota2, nota3);
        em.getTransaction().begin();
        alunoDao.cadastrar(novoAluno);
        em.getTransaction().commit();
        em.close();
        System.out.println("Aluno cadastrado com sucesso!");
    }

    private static void excluirAluno() {

    }

    private static void alterarAluno() {

    }


    public static void buscarAlunoPeloNome() {
        EntityManager em = JPAUtil.getEntityManager();
        System.out.println("Digite o nome do aluno: ");
        String nome = scanner.nextLine();
        Aluno a = new Aluno();

        String jpql = "SELECT a FROM Aluno a WHERE a.nome = :n";

        try {
            a = em.createQuery(jpql, Aluno.class)
                    .setParameter("n", nome)
                    .getSingleResult();
            System.out.println(a.getNome());
            System.out.println(a.getRa());
            System.out.println(a.getEmail());
            System.out.println(a.getNota1());
            System.out.println(a.getNota2());
            System.out.println(a.getNota3());

        } catch (NoResultException e) {
            System.out.println("Nenhum aluno encontrado com o nome " + nome);
        }
    }


    private static void listarAlunos() {
        EntityManager em = JPAUtil.getEntityManager();
        String jpql = "SELECT a FROM Aluno a";
        List<Aluno> alunos = em.createQuery(jpql, Aluno.class).getResultList();
        try {
            for (Aluno a : alunos) {
                System.out.println("---------------------");
                System.out.println(a.getNome());
                System.out.println(a.getRa());
                System.out.println(a.getEmail());
                System.out.println(a.getNota1());
                System.out.println(a.getNota2());
                System.out.println(a.getNota3());
            }
        }   catch(NoResultException e){
                System.out.println("Nenhum aluno encontrado na lista. ");
        }
    }
}
