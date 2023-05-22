import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Election {


  private int nullFederalDeputyVotes;

  private int federalDeputyProtestVotes;


  // Na prática guardaria uma hash do eleitor
  private Map<Voter, Integer> votersFederalDeputy = new HashMap<Voter, Integer>();

  private Map<String, FederalDeputy> federalDeputyCandidates = new HashMap<String, FederalDeputy>();

  private Map<Voter, FederalDeputy> tempFDVote = new HashMap<Voter, FederalDeputy>();

  private static final BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
    
  public static void print(String output) {
    System.out.println(output);
  }

  public static String readString() {
    try {
      return scanner.readLine();
    } catch (Exception e) {
      print("\nErro na leitura de entrada, digite novamente");
      return readString();
    }
  }

  public static int readInt() {
    try {
      return Integer.parseInt(readString());
    } catch (Exception e) {
      print("\nErro na leitura de entrada, digite novamente");
      return readInt();
    }
  }

  public static class Builder {
    protected String password;

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Election build() {
      if (password == null)
        throw new IllegalArgumentException("password mustn't be null");

      if (password.isEmpty())
        throw new IllegalArgumentException("password mustn't be empty");

      return new Election(this.password);
    }
  }

  protected Election(String password) {
    this.password = password;
    this.status = false;
    this.nullFederalDeputyVotes = 0;
    this.federalDeputyProtestVotes = 0;
  }

  protected Boolean isValid(String password) {
    return this.password.equals(password);
  }

  public void computeVote(Candidate candidate, Voter voter) {
    if (candidate instanceof FederalDeputy) {
      if (votersFederalDeputy.get(voter) != null && votersFederalDeputy.get(voter) >= 2)
        throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");

      if (tempFDVote.get(voter) != null && tempFDVote.get(voter).equals(candidate))
        throw new Warning("Você não pode votar mais de uma vez em um mesmo candidato");

      candidate.numVotes++;
      if (votersFederalDeputy.get(voter) == null) {
        votersFederalDeputy.put(voter, 1);
        tempFDVote.put(voter, (FederalDeputy) candidate);
      } else {
        votersFederalDeputy.put(voter, this.votersFederalDeputy.get(voter) + 1);
        tempFDVote.remove(voter);
      }
    }
  };

  public void computeNullVote(String type, Voter voter) {
    if (type.equals("FederalDeputy")) {
      if (this.votersFederalDeputy.get(voter) != null && this.votersFederalDeputy.get(voter) >= 2)
        throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");

      this.nullFederalDeputyVotes++;
      if (this.votersFederalDeputy.get(voter) == null)
        votersFederalDeputy.put(voter, 1);
      else
        votersFederalDeputy.put(voter, this.votersFederalDeputy.get(voter) + 1);
    }
  }

  public void computeProtestVote(String type, Voter voter) {
    if (type.equals("FederalDeputy")) {
      if (this.votersFederalDeputy.get(voter) != null && this.votersFederalDeputy.get(voter) >= 2)
        throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");

      this.federalDeputyProtestVotes++;
      if (this.votersFederalDeputy.get(voter) == null)
        votersFederalDeputy.put(voter, 1);
      else
        votersFederalDeputy.put(voter, this.votersFederalDeputy.get(voter) + 1);
    }
  }

  public boolean getStatus() {
    return this.status;
  }

  public void start(String password) {
    if (!isValid(password))
      throw new Warning("Senha inválida");

    this.status = true;
  }

  public void finish(String password) {
    if (!isValid(password))
      throw new Warning("Senha inválida");

    this.status = false;
  }

  // as subclasses vão sobrecarregar esse método
  public void addCandidate(Candidate candidate, String password){
    if (!isValid(password))
        throw new Warning("Senha inválida");

  }

  public void removeCandidate(Candidate candidate, String password){
    if (!isValid(password))
      throw new Warning("Senha inválida");
  }

  public FederalDeputy getFederalDeputyByNumber(String state, int number) {
    return this.federalDeputyCandidates.get(state + number);
  }

  public void addFederalDeputyCandidate(FederalDeputy candidate, String password) {


    if (this.federalDeputyCandidates.get(candidate.state + candidate.number) != null)
      throw new Warning("Numero de candidato indisponível");

    this.federalDeputyCandidates.put(candidate.state + candidate.number, candidate);
  }

  public void removeFederalDeputyCandidate(FederalDeputy candidate, String password) {


    this.federalDeputyCandidates.remove(candidate.state + candidate.number);
  }

  public String getResults(String password) {
    if (!isValid(password))
      throw new Warning("Senha inválida");

    if (this.status)
      throw new StopTrap("Eleição ainda não finalizou, não é possível gerar o resultado");

    var decimalFormater = new DecimalFormat("0.00");
    var presidentRank = new ArrayList<President>();
    var federalDeputyRank = new ArrayList<FederalDeputy>();

    var builder = new StringBuilder();

    builder.append("Resultado da eleicao:\n");

    // int totalVotesP = presidentProtestVotes + nullPresidentVotes;
    // for (Map.Entry<Integer, President> candidateEntry : presidentCandidates.entrySet()) {
    //   President candidate = candidateEntry.getValue();
    //   totalVotesP += candidate.numVotes;
    //   presidentRank.add(candidate);
    // }

    int totalVotesFD = federalDeputyProtestVotes + nullFederalDeputyVotes;
    for (Map.Entry<String, FederalDeputy> candidateEntry : federalDeputyCandidates.entrySet()) {
      FederalDeputy candidate = candidateEntry.getValue();
      totalVotesFD += candidate.numVotes;
      federalDeputyRank.add(candidate);
    }

    var sortedFederalDeputyRank = federalDeputyRank.stream()
        .sorted((o1, o2) -> o1.numVotes == o2.numVotes ? 0 : o1.numVotes < o2.numVotes ? 1 : -1)
        .collect(Collectors.toList());

    var sortedPresidentRank = presidentRank.stream()
        .sorted((o1, o2) -> o1.numVotes == o2.numVotes ? 0 : o1.numVotes < o2.numVotes ? 1 : -1)
        .collect(Collectors.toList());

    builder.append("  Votos presidente:\n");
    // builder.append("  Total: " + totalVotesP + "\n");
    // builder.append("  Votos nulos: " + nullPresidentVotes + " ("
    //     + decimalFormater.format((double) nullPresidentVotes / (double) totalVotesFD * 100) + "%)\n");
    // builder.append("  Votos brancos: " + presidentProtestVotes + " ("
    //     + decimalFormater.format((double) presidentProtestVotes / (double) totalVotesFD * 100) + "%)\n");
    builder.append("\tNumero - Partido - Nome  - Votos  - % dos votos totais\n");
    for (President candidate : sortedPresidentRank) {
      builder.append("\t" + candidate.number + " - " + candidate.party + " - " + candidate.name + " - "
          + candidate.numVotes + " - "
          //+ decimalFormater.format((double) candidate.numVotes / (double) totalVotesP * 100)
          + "%\n");
    }

    President electPresident = sortedPresidentRank.get(0);
    builder.append("\n\n  Presidente eleito:\n");
    // builder.append("  " + electPresident.name + " do " + electPresident.party + " com "
    //     + decimalFormater.format((double) electPresident.numVotes / (double) totalVotesP * 100) + "% dos votos\n");
    builder.append("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");

    builder.append("\n\n  Votos deputado federal:\n");
    builder.append("  Votos nulos: " + nullFederalDeputyVotes + " ("
        + decimalFormater.format((double) nullFederalDeputyVotes / (double) totalVotesFD * 100) + "%)\n");
    builder.append("  Votos brancos: " + federalDeputyProtestVotes + " ("
        + decimalFormater.format((double) federalDeputyProtestVotes / (double) totalVotesFD * 100) + "%)\n");
    builder.append("  Total: " + totalVotesFD + "\n");
    builder.append("\tNumero - Partido - Nome - Estado - Votos - % dos votos totais\n");
    for (FederalDeputy candidate : sortedFederalDeputyRank) {
      builder.append(
          "\t" + candidate.number + " - " + candidate.party + " - " + candidate.state + " - " + candidate.name + " - "
              + candidate.numVotes + " - "
              + decimalFormater.format((double) candidate.numVotes / (double) totalVotesFD * 100)
              + "%\n");
    }

    FederalDeputy firstDeputy = sortedFederalDeputyRank.get(0);
    FederalDeputy secondDeputy = sortedFederalDeputyRank.get(1);
    builder.append("\n\n  Deputados eleitos:\n");
    builder.append("  1º " + firstDeputy.name + " do " + firstDeputy.party + " com "
        + decimalFormater.format((double) firstDeputy.numVotes / (double) totalVotesFD * 100) + "% dos votos\n");
    builder.append("  2º " + secondDeputy.name + " do " + secondDeputy.party + " com "
        + decimalFormater.format((double) secondDeputy.numVotes / (double) totalVotesFD * 100) + "% dos votos\n");

    return builder.toString();
  }
}
