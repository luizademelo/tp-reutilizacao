import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import static java.lang.System.exit;

public class Controller {
    private static final BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));

    private static boolean exit = false;
  
    private static final Map<String, CertifiedProfessional> CertifiedMap = new HashMap<>();
  
    private static final Map<String, Voter> VoterMap = new HashMap<>();
  
    public static Eleicao currentElection;
  
    private static void print(String output) {
      System.out.println(output);
    }

    private static void createElection(String electionPassword, String type){
        currentElection = new Eleicao.Builder()
        .password(electionPassword)
        .type(type)
        .build();
    }
      
    public static void initializeElection(String electionPassword){
        try{
            while(!exit){
                print("Selecione o tipo de eleicao desejada:\n");
                print("(1) Presidencial");
                print("(2) Estadual"); 
                print("(3) Municipal"); 
                print("(4) Universitaria");
                print("(0) Fechar Aplicacao"); 
                int command = readInt(); 
                switch(command){
                    case 1: 
                        createElection(electionPassword, "Presidencial");
                        break; 
                    case 2: 
                        createElection(electionPassword, "Estadual");
                        break; 
                    case 3: 
                        createElection(electionPassword, "Municipal");
                        break; 
                    case 4: 
                        createElection(electionPassword, "Universitaria");
                        break; 
                    case 0: 
                        exit = true; 
                }
            }
        }catch(Exception e){
            print("Erro inesperado"); 
        }

    }
    
    public static Eleicao getElection(){
        return currentElection; 
    }

    private static String readString() {
      try {
        return scanner.readLine();
      } catch (Exception e) {
        print("\nErro na leitura de entrada, digite novamente");
        return readString();
      }
    }
  
    private static int readInt() {
      try {
        return Integer.parseInt(readString());
      } catch (Exception e) {
        print("\nErro na leitura de entrada, digite novamente");
        return readInt();
      }
    }


  
    public static void startMenu() {
      try {
        while (!exit) {
          print("Escolha uma opção:\n");
          print("(1) Entrar (Eleitor)");
          print("(2) Entrar (TSE)");
          print("(0) Fechar aplicação");
          int command = readInt();
          switch (command) {
            case 1: 
                voterMenu();
                break; 
            //case 2 -> tseMenu();
            case 0: 
                exit = true; 
                break;
            default:
                 print("Comando inválido\n");
          }
          print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        }
      } catch (Exception e) {
        print("Erro inesperado\n");
      }
    }
  
    private static Voter getVoter() {
      print("Insira seu título de eleitor:");
      String electoralCard = readString();
      Voter voter = VoterMap.get(electoralCard);
      if (voter == null) {
        print("Eleitor não encontrado, por favor confirme se a entrada está correta e tente novamente");
      } else {
        print("Olá, você é " + voter.name + " de " + voter.state + "?\n");
        print("(1) Sim\n(2) Não");
        int command = readInt();
        if (command == 1)
          return voter;
        else if (command == 2)
          print("Ok, você será redirecionado para o menu inicial");
        else {
          print("Entrada inválida, tente novamente");
          return getVoter();
        }
      }
      return null;
    }
  
    
  
    private static void voterMenu() {
      try {
        print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        if (!currentElection.getStatus()) {
          print("A eleição ainda não foi inicializada, verifique com um funcionário do TSE");
          return;
        }
  
        Voter voter = getVoter();
        if (voter == null)
          return;
        print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
  
        print("Vamos começar!\n");
        print(
            "OBS:\n- A partir de agora caso você queira votar nulo você deve usar um numero composto de 0 (00 e 0000)\n- A partir de agora caso você queira votar branco você deve escrever br\n");
        print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
  
    //     if (votePresident(voter))
    //       print("Voto para presidente registrado com sucesso");
    //     print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
  
    //     if (voteFederalDeputy(voter, 1))
    //       print("Primeiro voto para deputado federal registrado com sucesso");
    //     print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
  
    //     if (voteFederalDeputy(voter, 2))
    //       print("Segundo voto para deputado federal registrado com sucesso");
    //     print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
  
      } catch (Warning e) {
        print(e.getMessage());
      } catch (StopTrap e) {
        print(e.getMessage());
      } catch (Exception e) {
        print("Erro inesperado");
      }
    }
  

  

  
    private static void startSession(CertifiedProfessional tseProfessional) {
      try {
        print("Insira a senha da urna");
        String pwd = readString();
        //tseProfessional.startSession(currentElection, pwd);
        print("Sessão inicializada");
        print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
      } catch (Warning e) {
        print(e.getMessage());
      }
    }
  
    private static void endSession(CertifiedProfessional tseProfessional) {
      try {
        print("Insira a senha da urna:");
        String pwd = readString();
        //tseProfessional.endSession(currentElection, pwd);
        print("Sessão finalizada com sucesso");
        print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
      } catch (Warning e) {
        print(e.getMessage());
      }
    }
  
    private static void showResults(CertifiedProfessional tseProfessional) {
      try {
        print("Insira a senha da urna");
        String pwd = readString();
        //print(tseProfessional.getFinalResult(currentElection, pwd));
        print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
      } catch (Warning e) {
        print(e.getMessage());
      }
    }

    public static void loadVoters() {
        try {
          File myObj = new File("voterLoad.txt");
          Scanner myReader = new Scanner(myObj);
          while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            var voterData = data.split(",");
            VoterMap.put(voterData[0],
                new Voter.Builder().electoralCard(voterData[0]).name(voterData[1]).state(voterData[2]).build());
          }
          myReader.close();
        } catch (Exception e) {
          print("Erro na inicialização dos dados");
          exit(1);
        }
      }
    
    public static void loadProfessionals() {
        CertifiedMap.put("cert", new CertifiedProfessional.Builder()
            .user("cert")
            .password("54321")
            .build());
        CertifiedMap.put("emp", new CertifiedProfessional.Builder()
            .user("emp")
            .password("12345")
            .build());
      }

    protected static void addCandidate(CertifiedProfessional tseProfessional) {
      print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
      print("Qual a categoria de seu candidato?\n");
      print("(1) Presidente");
      print("(2) Deputado Federal");
      int candidateType = readInt();
  
      if (candidateType > 2 || candidateType < 1) {
        print("Comando inválido");
        addCandidate(tseProfessional);
      }
  
      print("Qual o nome do candidato?");
      String name = readString();
  
      print("Qual o numero do candidato?");
      int number = readInt();
  
      print("Qual o partido do candidato?");
      
      String party = readString();
      Candidate candidate = null;
      if (candidateType == 2) {
        print("Qual o estado do candidato?");
        String state = readString();
  
        print("\nCadastrar o candidato deputado federal " + name + " Nº " + number + " do " + party + "(" + state + ")?");
        candidate = new FederalDeputy.Builder()
            .name(name)
            .number(123)
            .party(party)
            .state(state)
            .build();
      } else if (candidateType == 1) {
        print("\nCadastrar o candidato a presidente " + name + " Nº " + number + " do " + party + "?");
        candidate = new President.Builder()
            .name(name)
            .number(123)
            .party(party)
            .build();
      }
  
      print("(1) Sim\n(2) Não");
      int save = readInt();
      if (save == 1 && candidate != null) {
        print("Insira a senha da urna");
        String pwd = readString();
        tseProfessional.addCandidate(candidate, currentElection, pwd);
        print("Candidato cadastrado com sucesso");
      }
}
