import java.util.HashMap;
import java.util.Map;

public class EleicaoPresidencial extends Eleicao{

    private int nullPresidentVotes;
    private int presidentProtestVotes;

    // Na prática guardaria uma hash do eleitor
    private Map<Voter, Integer> votersPresident = new HashMap<Voter, Integer>();

    private Map<Integer, President> presidentCandidates = new HashMap<Integer, President>();

    protected EleicaoPresidencial(String password) {
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
        //super.addCandidate(candidate, password);

        if (this.presidentCandidates.get(candidate.number) != null)
          throw new Warning("Numero de candidato indisponível");
    
        this.presidentCandidates.put(candidate.number, candidate);
    
      }
    
      public void removeCandidate(President candidate, String password) {
        //super.removeCandidate(candidate, password);
        this.presidentCandidates.remove(candidate.number);
      }

      
    
      
}
