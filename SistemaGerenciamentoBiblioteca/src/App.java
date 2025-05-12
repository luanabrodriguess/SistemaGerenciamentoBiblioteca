import java.util.Date;
import java.util.List;

import model.Livro;
import model.Usuario;
import model.Emprestimo;

import view.PreCarregadorDados;
import view.VisaoBiblioteca;

public class App {
    public static void main(String[] args) {
        try {

            PreCarregadorDados.carregarDadosIniciais();

            List<Livro> livros = PreCarregadorDados.getLivros();
            List<Usuario> usuarios = PreCarregadorDados.getUsuarios();
            List<Emprestimo> emprestimos = PreCarregadorDados.getEmprestimos();

            Livro livro1 = new Livro("O Senhor dos Anéis", "J.R.R. Tolkien", 1954, 5, "Fantasia");
            Usuario usuario1 = new Usuario("João Silva", "123456789", "Rua X, 123", "joao@email.com");

            Date dataEmprestimo = new Date();
            Date dataDevolucaoPrevista = new Date(dataEmprestimo.getTime() + (7L * 24 * 60 * 60 * 1000)); // 7 dias
                                                                                                          // depois
            Emprestimo emprestimo1 = new Emprestimo(livro1, usuario1, dataEmprestimo, dataDevolucaoPrevista);

            System.out.println("Empréstimo realizado!");
            System.out.println("Livro: " + livro1.getTitulo());
            System.out.println("Usuário: " + usuario1.getNome());
            System.out.println("Data do Empréstimo: " + dataEmprestimo);
            System.out.println("Data de Devolução Prevista: " + dataDevolucaoPrevista);

            livro1.emprestarLivro();
            System.out.println("Número de exemplares após empréstimo: " + livro1.getNumExemplares());

            Date dataDevolucao = new Date();
            emprestimo1.setDataDevolucaoReal(dataDevolucao);
            livro1.devolverLivro();
            System.out.println("Número de exemplares após devolução: " + livro1.getNumExemplares());

            int atraso = emprestimo1.calcularAtraso();
            System.out.println("Dias de atraso: " + atraso);

            VisaoBiblioteca visao = new VisaoBiblioteca(livros, usuarios, emprestimos);
            visao.exibirMenu();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
