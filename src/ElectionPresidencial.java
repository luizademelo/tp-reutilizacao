import java.util.HashMap;
import java.util.Map;

public class ElectionPresidencial extends Election{

    private int nullPresidentVotes;
    private int presidentProtestVotes;

    // Na prática guardaria uma hash do eleitor
    private Map<Voter, Integer> votersPresident = new HashMap<Voter, Integer>();

    private Map<Integer, President> presidentCandidates = new HashMap<Integer, President>();

    protected ElectionPresidencial(String password) {
        super(password); 
        this.nullPresidentVotes = 0;
        this.presidentProtestVotes = 0;
    }

    public void computeVote(Candidate candidate, Voter voter) {
        if (candidate instanceof President) {
          if (votersPresident.get(voter) != null && votersPresident.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para presidente");
    
          candidate.numVotes++;
          votersPresident.put(voter, 1);
        } 
    }

    public void computeNullVote(String type, Voter voter) {
        if (type.equals("President")) {
          if (this.votersPresident.get(voter) != null && votersPresident.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para presidente");
    
          this.nullPresidentVotes++;
          votersPresident.put(voter, 1);
        } 
      }

      public void computeProtestVote(String type, Voter voter) {
        if (type.equals("President")) {
          if (this.votersPresident.get(voter) != null && votersPresident.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para presidente");
    
          this.presidentProtestVotes++;
          votersPresident.put(voter, 1);
        } 
      }

      public President getPresidentByNumber(int number) {
        return this.presidentCandidates.get(number);
      }
    
      public void addCandidate(President candidate, String password) {
        super.addCandidate(candidate, password);

        if (this.presidentCandidates.get(candidate.number) != null)
          throw new Warning("Numero de candidato indisponível");
    
        this.presidentCandidates.put(candidate.number, candidate);
    
      }
    
      public void removeCandidate(President candidate, String password) {
        super.removeCandidate(candidate, password);
    
        this.presidentCandidates.remove(candidate.number);
      }

      private static boolean vote(Voter voter, ElectionPresidencial currentElection) {
        print("(ext) Desistir");
        print("Digite o número do candidato escolhido por você para presidente:");
        String vote = readString();
        if (vote.equals("ext"))
          throw new StopTrap("Saindo da votação");
        // Branco
        else if (vote.equals("br")) {
          print("Você está votando branco\n");
          print("(1) Confirmar\n(2) Mudar voto");
          int confirm = readInt();
          if (confirm == 1) {
            voter.vote(0, currentElection, "President", true);
            return true;
          } else
            vote(voter, currentElection);
        } else {
          try {
            int voteNumber = Integer.parseInt(vote);
            // Nulo
            if (voteNumber == 0) {
              print("Você está votando nulo\n");
              print("(1) Confirmar\n(2) Mudar voto");
              int confirm = readInt();
              if (confirm == 1) {
                voter.vote(0, currentElection, "President", false);
                return true;
              } else
                vote(voter, currentElection);
            }
    
            // Normal
            President candidate = currentElection.getPresidentByNumber(voteNumber);
            if (candidate == null) {
              print("Nenhum candidato encontrado com este número, tente novamente");
              print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
              return vote(voter, currentElection);
            }
            print(candidate.name + " do " + candidate.party + "\n");
            print("(1) Confirmar\n(2) Mudar voto");
            int confirm = readInt();
            if (confirm == 1) {
              voter.vote(voteNumber, currentElection, "President", false);
              return true;
            } else if (confirm == 2)
              return vote(voter, currentElection);
          } catch (Warning e) {
            print(e.getMessage());
            return vote(voter, currentElection);
          } catch (Error e) {
            print(e.getMessage());
            throw e;
          } catch (Exception e) {
            print("Ocorreu um erro inesperado");
            return false;
          }
        }
        return true;
    
      }
}
