public class Urna {

    public static void main(String[] args) {
        
    String electionPassword = "password";

    // Startup the current election instance
    Controller.initializeElection(electionPassword); 


    President presidentCandidate1 = new President.Builder().name("João").number(123).party("PDS1").build();
    Controller.currentElection.addPresidentCandidate(presidentCandidate1, electionPassword);
    President presidentCandidate2 = new President.Builder().name("Maria").number(124).party("ED").build();
    Controller.currentElection.addPresidentCandidate(presidentCandidate2, electionPassword);
    FederalDeputy federalDeputyCandidate1 = new FederalDeputy.Builder().name("Carlos").number(12345).party("PDS1")
        .state("MG").build();
    Controller.currentElection.addFederalDeputyCandidate(federalDeputyCandidate1, electionPassword);
    FederalDeputy federalDeputyCandidate2 = new FederalDeputy.Builder().name("Cleber").number(54321).party("PDS2")
        .state("MG").build();
    Controller.currentElection.addFederalDeputyCandidate(federalDeputyCandidate2, electionPassword);
    FederalDeputy federalDeputyCandidate3 = new FederalDeputy.Builder().name("Sofia").number(11211).party("IHC")
        .state("MG").build();
    Controller.currentElection.addFederalDeputyCandidate(federalDeputyCandidate3, electionPassword);

    // Startar todo os eleitores e profissionais do TSE
    Controller.loadVoters();
    Controller.loadTSEProfessionals();

    Controller.startMenu();
  }
}
