import java.io.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String raiz = "Aplicativo/";
        String raizFilmes = raiz + "Filmes/";
        String raizSeries = raiz + "Séries/";
        String raizUsuarios = raiz + "Usuários/";
        String arqIdFilme = raiz + "idFilme.txt";
        String arqIdSerie = raiz + "idSerie.txt";
        String arqIdUsuario = raiz + "idUsuario.txt";
        int opcao;

        do {
            menuPrincipal();
            opcao = sc.nextInt();
            switch (opcao) {
                case 1:
                    gerenciarFilmes(raizFilmes, arqIdFilme);
                    break;
                case 2:
                    gerenciarSeries(raizSeries, arqIdSerie);
                    break;
                case 3:
                    gerenciarUsuarios(raizFilmes, raizSeries, raizUsuarios, arqIdFilme, arqIdSerie, arqIdUsuario);
                    break;
                case 4:
                    iniciarResetar(raiz, raizFilmes, raizSeries, raizUsuarios, arqIdFilme, arqIdSerie, arqIdUsuario);
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
            }
        } while (opcao != 5);
    }

    private static void gerenciarFilmes(String raizFilmes, String arqIdFilme) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            menuSecundario("Filmes");
            opcao = sc.nextInt();
            switch (opcao) {
                case 1: //cadastrar
                    cadastrarFilme(raizFilmes, arqIdFilme);
                    break;
                case 2://buscar
                    buscarFilme(raizFilmes, arqIdFilme);
                    break;
                case 3://listar
                    listar("filme", raizFilmes);
                    break;
                case 4://atualizar
                    atualizar("filme", raizFilmes);
                    break;
                case 5://apagar
                    apagarFS("filme", raizFilmes);
                    break;
                case 6://voltar
                    System.out.println("Voltando");
                    break;
            }
        } while (opcao != 6);
    }


    private static Filme criarEditarFilme(int id) {
        Scanner sc = new Scanner(System.in);
        Filme f = new Filme();
        f.id = id;
        System.out.println("Informe o nome do filme: ");
        f.nome = sc.nextLine();
        System.out.println("Informe a data de estréia do filme (DD/MM/AAAA): ");
        int data = sc.nextInt();
        f.data = new Estreia();
        f.data.dia = data / 1000000;
        f.data.mês = (data % 1000000) / 10000;
        f.data.ano = (data % 1000000) % 10000;
        System.out.println("Informe qual a minutagem total do filme: ");
        int min = sc.nextInt();
        f.tempo = new Tempo();
        f.tempo.horas = min / 60;
        f.tempo.minutos = min % 60;
        f.genero = new String[10];
        int i = 0;
        char opcao;
        sc.nextLine();
        do {
            System.out.print("Gênero: ");
            f.genero[i] = sc.nextLine();
            i++;
            System.out.print("Quer adicionar mais um gênero a esse filme? (s/n): ");
            opcao = sc.next().charAt(0);
            sc.nextLine();
        } while (opcao == 's' && i <= 10);

        return f;
    }

    private static boolean gravarFilme(Filme f, String raizFilmes) {
        try {
            PrintWriter pw = new PrintWriter(raizFilmes + f.id);
            pw.append("ID: " + f.id + "\n");
            pw.append("Nome: " + f.nome + "\n");
            pw.append("Estreia: " + f.data.dia + "/" + f.data.mês + "/" + f.data.ano + "\n");
            pw.append("Duração: " + f.tempo.horas + "h " + f.tempo.minutos + "m\n");
            for (int i = 0; i < 10; i++) {
                if (f.genero[i] != null) {
                    pw.append("Gênero " + (i + 1) + ": " + f.genero[i] + "\n");
                }
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void cadastrarFilme(String raizFilmes, String arqIdFilme) {
        int id = leId(arqIdFilme);
        Filme f = criarEditarFilme(id);
        if (gravarFilme(f, raizFilmes)) {
            gravaId(++id, arqIdFilme);
        } else {
            System.out.println("Não foi possível gravar o filme!");
        }
    }

    private static void buscarFilme(String raizFilmes, String arqIdFilme) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nQual tipo de buscar você quer utilizar: ");
        menuDeBusca();
        int opcao = sc.nextInt();
        switch (opcao) {
            case 1:
                buscaPorId("filme", raizFilmes);
                break;
            case 2:
                buscaPorNome("filme", raizFilmes);
                break;
            case 3:
                buscaPorAno("filme", raizFilmes);
                break;
        }
    }

    private static void gerenciarSeries(String raizSeries, String arqIdSerie) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            menuSecundario("Séries");
            opcao = sc.nextInt();
            switch (opcao) {
                case 1: //cadastrar
                    cadastrarSerie(raizSeries, arqIdSerie);
                    break;
                case 2://buscar
                    buscarSerie(raizSeries, arqIdSerie);
                    break;
                case 3://listar
                    listar("série", raizSeries);
                    break;
                case 4://atualizar
                    atualizar("série", raizSeries);
                    break;
                case 5://apagar
                    apagarFS("série", raizSeries);
                    break;
                case 6://voltar
                    System.out.println("Voltando");
                    break;
            }
        } while (opcao != 6);
    }

    private static Série criarEditarSerie(int id) {
        Scanner sc = new Scanner(System.in);
        Série s = new Série();
        s.id = id;
        System.out.println("Informe o nome da série: ");
        s.nome = sc.nextLine();
        System.out.println("Informe a data de estréia da série (DD/MM/AAAA): ");
        int data = sc.nextInt();
        s.data = new Estreia();
        s.data.dia = data / 1000000;
        s.data.mês = (data % 1000000) / 10000;
        s.data.ano = (data % 1000000) % 10000;
        System.out.println("Informe quantas temporadas têm a série: ");
        s.temporadas = sc.nextInt();
        System.out.println("Informe quantos episódios totais têm a série: ");
        s.episodios = sc.nextInt();
        s.genero = new String[10];
        int i = 0;
        char opcao;
        sc.nextLine();
        do {
            System.out.print("Gênero: ");
            s.genero[i] = sc.nextLine();
            i++;
            System.out.print("Quer adicionar mais um gênero a essa série? (s/n): ");
            opcao = sc.next().charAt(0);
            sc.nextLine();
        } while (opcao == 's' && i <= 10);
        return s;
    }

    private static boolean gravarSerie(Série s, String raizSeries) {
        try {
            PrintWriter pw = new PrintWriter(raizSeries + s.id); //o id é o nome do arquivo
            pw.append("ID: " + s.id + "\n");
            pw.append("Nome: " + s.nome + "\n");
            pw.append("Estreia: " + s.data.dia + "/" + s.data.mês + "/" + s.data.ano + "\n");
            pw.append("Temporadas: " + s.temporadas + "\n");
            pw.append("Episódios: " + s.episodios + "\n");
            for (int i = 0; i < 10; i++) {
                if (s.genero[i] != null) {
                    pw.append("Gênero " + (i + 1) + ": " + s.genero[i] + "\n");
                }
            }
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void cadastrarSerie(String raizSeries, String arqIdSerie) {
        int id = leId(arqIdSerie);
        Série s = criarEditarSerie(id);
        if (gravarSerie(s, raizSeries)) {
            gravaId(++id, arqIdSerie);
        } else {
            System.out.println("Não foi possível gravar a série!");
        }
    }

    private static void buscarSerie(String raizSeries, String arqIdSerie) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nQual tipo de buscar você quer utilizar: ");
        menuDeBusca();
        int opcao = sc.nextInt();
        switch (opcao) {
            case 1:
                buscaPorId("série", raizSeries);
                break;
            case 2:
                buscaPorNome("série", raizSeries);
                break;
            case 3:
                buscaPorAno("série", raizSeries);
                break;
        }
    }

    private static void gerenciarUsuarios(String raizFilmes, String raizSeries, String raizUsuarios, String arqIdFilme, String arqIdSerie, String arqIdUsuario) {
        Scanner sc = new Scanner(System.in);
        int opcao;
        do {
            menuSecundario("Usuários");
            opcao = sc.nextInt();
            switch (opcao) {
                case 1: //cadastrar

                    break;
                case 2://buscar
                    break;
                case 3://listar
                    break;
                case 4://atualizar
                    break;
                case 5://apagar
                    break;
                case 6://voltar
                    System.out.println("Voltando");
                    break;
            }
        } while (opcao != 6);
    }

    private static void menuPrincipal() {
        System.out.println("\n-----------------------"
                + "\n1) Filmes"
                + "\n2) Séries"
                + "\n3) Usuários"
                + "\n4) Iniciar/Resetar"
                + "\n5) Sair"
                + "\n-----------------------");
        System.out.println("Opção: ");

    }

    private static void menuSecundario(String menu) {
        System.out.println("\n" + menu);
        System.out.println("-----------------------"
                + "\n1) Gravar"
                + "\n2) Buscar"
                + "\n3) Listar"
                + "\n4) Atualizar"
                + "\n5) Apagar"
                + "\n6) Voltar"
                + "\n-----------------------");
        System.out.println("Opção: ");

    }

    private static void menuDeBusca() {
        System.out.println("\n-----------------------" +
                "\n1) Id" +
                "\n2) Nome" +
                "\n3) Ano de Lançamento" +
                "\n-----------------------");
        System.out.println("Opção: ");
    }

    private static void iniciarResetar(String raiz, String raizFilmes, String raizSeries, String raizUsuarios,
                                       String arqIdFilme, String arqIdSerie, String arqIdUsuario) {
        File dir = new File(raiz);
        if (!dir.exists()) {
            dir.mkdir();
        }
        dir = new File(raizFilmes);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            apagaArquivos(dir);
        }
        dir = new File(raizSeries);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            apagaArquivos(dir);
        }
        dir = new File(raizUsuarios);
        if (!dir.exists()) {
            dir.mkdir();
        } else {
            apagaArquivos(dir);
        }
        gravaId(0, arqIdFilme);
        gravaId(0, arqIdSerie);
        gravaId(0, arqIdUsuario);
    }

    private static int leId(String arquivo) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(arquivo));
            int id = Integer.parseInt(br.readLine());
            br.close();
            return id;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao ler o id");
            e.printStackTrace();
        }
        return -1;
    }

    private static void gravaId(int i, String arquivo) {
        try {
            PrintWriter pw = new PrintWriter(arquivo);
            pw.println(i);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    private static void apagaArquivos(File dir) {
        File[] arquivos = dir.listFiles();
        if (arquivos != null) {
            for (File f : arquivos) {
                f.delete();
            }
        }
    }

    private static void buscaPorId(String tipo, String raiz) {
        Scanner sc = new Scanner(System.in);
        if (tipo.equals("filme")) {
            System.out.println("Informe o Id do filme que deseja buscar: ");
        }
        if (tipo.equals("série")) {
            System.out.println("Informe o Id da série que deseja buscar: ");
        }
        int id = sc.nextInt();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(raiz + id));
            if (tipo.equals("filme")) {
                System.out.println("\n--- Filme encontrado (" + id + ") ---");
            }
            if (tipo.equals("série")) {
                System.out.println("\n--- Série encontrada (" + id + ") ---");
            }
            String linha;
            while ((linha = br.readLine()) != null) {
                System.out.println(linha);
            }
        } catch (FileNotFoundException e) {
            if (tipo.equals("filme")) {
                System.out.println("Nenhum filme cadastrado.");
            }
            if (tipo.equals("série")) {
                System.out.println("Nenhuma série cadastrada.");
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: id");
        }
    }

    private static void buscaPorNome(String tipo, String raiz) {
        Scanner sc = new Scanner(System.in);
        if (tipo.equals("filme")) {
            System.out.println("Informe o nome do filme que deseja buscar: ");
        }
        if (tipo.equals("série")) {
            System.out.println("Informe o nome da série que deseja buscar: ");
        }
        String nome = sc.nextLine();
        File pasta = new File(raiz);
        File[] arquivos = pasta.listFiles();
        if (arquivos == null || arquivos.length == 0) {
            if (tipo.equals("filme")) {
                System.out.println("Nenhum filme cadastrado.");
            }
            if (tipo.equals("série")) {
                System.out.println("Nenhuma série cadastrada.");
            }
            return;
        }
        boolean encontrado = false;
        for (File arq : arquivos) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(arq));
                String linha;
                String nomeFilme = "";

                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith("Nome:")) {
                        nomeFilme = linha.substring(6);
                        break;
                    }
                }
                br.close();
                if (nomeFilme.contains(nome)) {
                    encontrado = true;
                    if (tipo.equals("filme")) {
                        System.out.println("\n--- Filme encontrado (" + arq.getName() + ") ---");
                    }
                    if (tipo.equals("série")) {
                        System.out.println("\n--- Série encontrada (" + arq.getName() + ") ---");
                    }
                    BufferedReader br2 = new BufferedReader(new FileReader(arq));
                    while ((linha = br2.readLine()) != null) {
                        System.out.println(linha);
                    }
                    br2.close();
                }
            } catch (Exception e) {
                System.out.println("Erro ao ler arquivo: " + arq.getName());
            }
        }

        if (!encontrado) {
            System.out.println("Nenhum filme encontrado com esse nome.");
        }
    }

    private static void buscaPorAno(String tipo, String raiz) {
        Scanner sc = new Scanner(System.in);
        if (tipo.equals("filme")) {
            System.out.println("Informe o ano do filme que deseja buscar: ");
        }
        if (tipo.equals("série")) {
            System.out.println("Informe o ano da série que deseja buscar: ");
        }

        int ano = sc.nextInt();

        File pasta = new File(raiz);
        File[] arquivos = pasta.listFiles();

        if (arquivos == null || arquivos.length == 0) {
            if (tipo.equals("filme")) {
                System.out.println("Nenhum filme cadastrado.");
            }
            if (tipo.equals("série")) {
                System.out.println("Nenhuma série cadastrada.");
            }
            return;
        }

        boolean encontrado = false;

        for (File arq : arquivos) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(arq));
                String linha;
                int anoFilme = -1;

                // Procura a linha "Data: DD/MM/AAAA"
                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith("Data:")) {
                        // Exemplo: "Data: 12/05/2015"
                        String dataStr = linha.substring(6).trim();

                        // Divide a data
                        String[] partes = dataStr.split("/");

                        // Ano é a última parte
                        anoFilme = Integer.parseInt(partes[2]);
                        break;
                    }
                }
                br.close();
                if (anoFilme == ano) {
                    encontrado = true;
                    if (tipo.equals("filme")) {
                        System.out.println("\n--- Filme encontrado (" + arq.getName() + ") ---");
                    }
                    if (tipo.equals("série")) {
                        System.out.println("\n--- Série encontrada (" + arq.getName() + ") ---");
                    }
                    BufferedReader br2 = new BufferedReader(new FileReader(arq));
                    while ((linha = br2.readLine()) != null) {
                        System.out.println(linha);
                    }
                    br2.close();
                }
            } catch (Exception e) {
                System.out.println("Erro ao ler arquivo: " + arq.getName());
            }
        }
        if (!encontrado) {
            System.out.println("Nenhum filme encontrado para este ano.");
        }
    }

    private static void listar(String tipo, String raiz) {
        File pasta = new File(raiz);
        File[] arquivos = pasta.listFiles();
        if (arquivos == null || arquivos.length == 0) {
            if (tipo.equals("filme")) {
                System.out.println("Nenhum filme cadastrado.");
            }
            if (tipo.equals("série")) {
                System.out.println("Nenhuma série cadastrada.");
            }
            return;
        }
        if (tipo.equals("filme")) {
            System.out.println("\n--- Filmes ---");
        }
        if (tipo.equals("série")) {
            System.out.println("\n--- Séries ---");
        }
        for (File arq : arquivos) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(arq));
                String linha;
                String nomeFilme = "";

                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith("Nome:")) {
                        nomeFilme = linha.substring(6);
                        break;
                    }
                }
                br.close();
                if (tipo.equals("filme")) {
                    System.out.println("\n--- Filme (" + arq.getName() + ") ---");
                }
                if (tipo.equals("série")) {
                    System.out.println("\n--- Série (" + arq.getName() + ") ---");
                }
                BufferedReader br2 = new BufferedReader(new FileReader(arq));
                while ((linha = br2.readLine()) != null) {
                    System.out.println(linha);
                }
                br2.close();
            } catch (Exception e) {
                System.out.println("Erro ao ler arquivo: " + arq.getName());
            }
        }

    }

    private static void atualizar(String tipo, String raiz) {
        Scanner sc = new Scanner(System.in);
        menuDeBusca();
        int opcao = sc.nextInt();
        switch (opcao) {
            case 1:
                if (tipo.equals("filme")) {
                    System.out.println("Informe o Id do filme que deseja atualizar: ");
                }
                if (tipo.equals("série")) {
                    System.out.println("Informe o Id da série que deseja atualizar: ");
                }
                int id = sc.nextInt();
                if (tipo.equals("filme")) {
                    System.out.println("Escreva novamente as informações do filme: ");
                    Filme f = criarEditarFilme(id);
                    gravarFilme(f, raiz);
                }
                if (tipo.equals("série")) {
                    System.out.println("Escreva novamente as informações da série: ");
                    Série s = criarEditarSerie(id);
                    gravarSerie(s, raiz);
                }
                break;
            case 2:
                sc.nextLine();
                if (tipo.equals("filme")) {
                    System.out.println("Informe o nome do filme que deseja atualizar: ");
                }
                if (tipo.equals("série")) {
                    System.out.println("Informe o nome da série que deseja atualizar: ");
                }
                String nome = sc.nextLine();
                if (tipo.equals("filme")) {
                    int id2 = enviarIdNome(tipo, raiz, nome);
                    if (id2 != -1) {
                        System.out.println("Escreva novamente as informações do filme: ");
                        Filme f = criarEditarFilme(id2);
                        gravarFilme(f, raiz);
                    } else {
                        System.out.println("Esse filme não foi cadastrado.");
                    }

                }
                if (tipo.equals("série")) {
                    int id2 = enviarIdNome(tipo, raiz, nome);
                    if (id2 != -1) {
                        System.out.println("Escreva novamente as informações da série: ");
                        Série s = criarEditarSerie(id2);
                        gravarSerie(s, raiz);
                    } else {
                        System.out.println("Essa série não foi cadastrada.");
                    }
                }
                break;
            case 3:

                break;
        }
    }

    public static int enviarIdNome(String tipo, String raiz, String nome) {
        File pasta = new File(raiz);
        File[] arquivos = pasta.listFiles();
        if (arquivos == null || arquivos.length == 0) {
            if (tipo.equals("filme")) {
                System.out.println("Nenhum filme cadastrado.");
            }
            if (tipo.equals("série")) {
                System.out.println("Nenhuma série cadastrada.");
            }
            return -1;
        }
        for (File arq : arquivos) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(arq));
                String linha;
                String id2;
                int id = 0;

                String nomeFilme = "";

                while ((linha = br.readLine()) != null) {
                    if (linha.startsWith("Nome:")) {
                        nomeFilme = linha.substring(6);
                        break;
                    }
                }
                BufferedReader br2 = new BufferedReader(new FileReader(arq));
                while ((linha = br2.readLine()) != null) {
                    if (linha.startsWith("ID:")) {
                        id2 = linha.substring(4);
                        id = Integer.parseInt(id2);
                    }
                }
                br.close();
                br2.close();
                if (nomeFilme.contains(nome)) {
                    return id;
                }
            } catch (Exception e) {
                System.out.println("Erro ao ler arquivo: " + arq.getName());
            }

        }
        return -1;
    }

    private static void apagarFS(String tipo, String raiz) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Informe o tipo de exclusão que quer seguir: ");
        menuDeBusca();
        int opcao = sc.nextInt();
        if (tipo.equals("filme")) {
            switch (opcao) {
                case 1:
                    apagarPorId("filme", raiz);
                    break;
                case 2:
                    apagarPorNome("filme", raiz);
            }

        }
        if (tipo.equals("série")) {
            switch (opcao) {
                case 1:
                    apagarPorId("série", raiz);
                    break;
                case 2:
                    apagarPorNome("série", raiz);
            }

        }
    }

    private static void apagarPorId(String tipo, String raiz) {
        Scanner sc = new Scanner(System.in);
        if (tipo.equals("filme")) {
            System.out.println("Informe o id do filme que quer excluir: ");
            int id = sc.nextInt();
            File dir = new File(raiz + id);
            if (dir.delete()) {
                dir.delete();
                System.out.println("O filme foi excluído com sucesso!");
            } else {
                System.out.println("O filme não pôde ser excluído!");
            }
        }
        if (tipo.equals("série")) {
            System.out.println("Informe o id da série que quer excluir: ");
            int id = sc.nextInt();
            File dir = new File(raiz + id);
            if (dir.delete()) {
                dir.delete();
                System.out.println("A série foi excluída com sucesso!");
            } else {
                System.out.println("A série não pôde ser excluída!");
            }
        }
    }
    private static void apagarPorNome(String tipo, String raiz) {
        Scanner sc = new Scanner(System.in);
        if (tipo.equals("filme")) {
            System.out.println("Informe o nome do filme que quer excluir: ");
            String nome = sc.nextLine();
            int id = enviarIdNome(tipo, raiz, nome);
            File dir = new File(raiz + id);
            if (dir.delete()) {
                dir.delete();
                System.out.println("O filme foi excluído com sucesso!");
            } else {
                System.out.println("O filme não pôde ser excluído!");
            }
        }
        if (tipo.equals("série")) {
            System.out.println("Informe o nome da série que quer excluir: ");
            String nome = sc.nextLine();
            int id = enviarIdNome(tipo, raiz, nome);
            File dir = new File(raiz + id);
            if (dir.delete()) {
                dir.delete();
                System.out.println("A série foi excluída com sucesso!");
            } else {
                System.out.println("A série não pôde ser excluída!");
            }
        }
    }

}

