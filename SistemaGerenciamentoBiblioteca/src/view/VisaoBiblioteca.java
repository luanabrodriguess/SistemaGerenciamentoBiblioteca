package view;

import model.Livro;
import model.Usuario;
import model.Emprestimo;
import model.OperacaoInvalidaException;

import java.util.List;
import java.util.Scanner;
import java.util.Date;
import java.util.ArrayList;

public class VisaoBiblioteca {

    private final List<Livro> livros;
    private final List<Usuario> usuarios;
    private final List<Emprestimo> emprestimos;

    public VisaoBiblioteca(List<Livro> livros, List<Usuario> usuarios, List<Emprestimo> emprestimos) {
        this.livros = livros;
        this.usuarios = usuarios;
        this.emprestimos = emprestimos;
    }

    public void exibirMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;
        do {
            System.out.println("\n=== Sistema de Gerenciamento de Biblioteca ===");
            System.out.println("1. Listar Livros");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Listar Empréstimos");
            System.out.println("4. Gerar Relatório de Atrasos");
            System.out.println("5. Gerar Relatório de Empréstimos Ativos");
            System.out.println("6. Realizar Empréstimo");
            System.out.println("7. Realizar Devolução");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Por favor, digite um número entre 1 e 8.");
                continue;
            }

            switch (opcao) {
                case 1:
                    listarLivros();
                    break;
                case 2:
                    listarUsuarios();
                    break;
                case 3:
                    listarEmprestimos();
                    break;
                case 4:
                    gerarRelatorioAtrasos();
                    break;
                case 5:
                    gerarRelatorioEmprestimosAtivos();
                    break;
                case 6:
                    realizarEmprestimo(scanner);
                    break;
                case 7:
                    realizarDevolucao(scanner);
                    break;
                case 8:
                    System.out.println("Saindo do sistema. Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha entre 1 e 8.");
            }
        } while (opcao != 8);
        scanner.close();
    }

    public void listarLivros() {
        System.out.println("\n=== Lista de Livros ===");
        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    public void listarUsuarios() {
        System.out.println("\n=== Lista de Usuários ===");
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }

    public void listarEmprestimos() {
        System.out.println("\n=== Lista de Empréstimos ===");
        for (Emprestimo emprestimo : emprestimos) {
            System.out.println(emprestimo);
        }
    }

    private void gerarRelatorioAtrasos() {
        System.out.println("\n=== Relatório de Atrasos ===");
        boolean temAtrasos = false;
        Date hoje = new Date();
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getDataDevolucaoReal() == null && hoje.after(emprestimo.getDataDevolucaoPrevista())) {
                System.out.println(emprestimo);
                temAtrasos = true;
            }
        }
        if (!temAtrasos) {
            System.out.println("Nenhum atraso encontrado.");
        }
    }

    private void gerarRelatorioEmprestimosAtivos() {
        System.out.println("\n=== Relatório de Empréstimos Ativos ===");
        boolean temAtivos = false;
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getDataDevolucaoReal() == null) {
                System.out.println(emprestimo);
                temAtivos = true;
            }
        }
        if (!temAtivos) {
            System.out.println("Nenhum empréstimo ativo encontrado.");
        }
    }

    private void realizarEmprestimo(Scanner scanner) {
        System.out.println("\n=== Realizar Empréstimo ===");
        if (livros.isEmpty() || usuarios.isEmpty()) {
            System.out.println("Não há livros ou usuários cadastrados para realizar empréstimo.");
            return;
        }

        System.out.println("Selecione o livro pelo número:");
        for (int i = 0; i < livros.size(); i++) {
            System.out.println((i + 1) + ". " + livros.get(i));
        }
        int livroIndex = lerIndice(scanner, livros.size());
        if (livroIndex == -1)
            return;
        Livro livroSelecionado = livros.get(livroIndex);

        if (livroSelecionado.getNumExemplares() <= 0) {
            System.out.println("Não há exemplares disponíveis para este livro.");
            return;
        }

        System.out.println("Selecione o usuário pelo número:");
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.println((i + 1) + ". " + usuarios.get(i));
        }
        int usuarioIndex = lerIndice(scanner, usuarios.size());
        if (usuarioIndex == -1)
            return;
        Usuario usuarioSelecionado = usuarios.get(usuarioIndex);

        Date dataEmprestimo = new Date();
        Date dataDevolucaoPrevista = new Date(dataEmprestimo.getTime() + (7L * 24 * 60 * 60 * 1000));

        try {
            Emprestimo novoEmprestimo = new Emprestimo(livroSelecionado, usuarioSelecionado, dataEmprestimo,
                    dataDevolucaoPrevista);
            emprestimos.add(novoEmprestimo);
            livroSelecionado.emprestarLivro();
            System.out.println("Empréstimo realizado com sucesso!");
        } catch (OperacaoInvalidaException e) {
            System.out.println("Erro ao realizar empréstimo: " + e.getMessage());
        }
    }

    private void realizarDevolucao(Scanner scanner) {
        System.out.println("\n=== Realizar Devolução ===");
        List<Emprestimo> emprestimosAtivos = new ArrayList<>();
        for (Emprestimo e : emprestimos) {
            if (e.getDataDevolucaoReal() == null) {
                emprestimosAtivos.add(e);
            }
        }

        if (emprestimosAtivos.isEmpty()) {
            System.out.println("Não há empréstimos ativos para devolução.");
            return;
        }

        System.out.println("Selecione o empréstimo para devolução pelo número:");
        for (int i = 0; i < emprestimosAtivos.size(); i++) {
            System.out.println((i + 1) + ". " + emprestimosAtivos.get(i));
        }
        int emprestimoIndex = lerIndice(scanner, emprestimosAtivos.size());
        if (emprestimoIndex == -1)
            return;
        Emprestimo emprestimoSelecionado = emprestimosAtivos.get(emprestimoIndex);

        Date dataDevolucao = new Date();
        emprestimoSelecionado.setDataDevolucaoReal(dataDevolucao);
        emprestimoSelecionado.getLivro().devolverLivro();
        System.out.println("Devolução realizada com sucesso!");
    }

    private int lerIndice(Scanner scanner, int tamanhoLista) {
        System.out.print("Digite o número correspondente: ");
        int indice = -1;
        try {
            indice = Integer.parseInt(scanner.nextLine()) - 1;
            if (indice < 0 || indice >= tamanhoLista) {
                System.out.println("Número inválido. Operação cancelada.");
                return -1;
            }
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Operação cancelada.");
            return -1;
        }
        return indice;
    }
}
