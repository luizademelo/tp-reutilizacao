public class Urna {

    public static void main(String[] args) {

        String electionPassword = "password";

        Controller.initializeElection(electionPassword);

        // Startar todo os eleitores e profissionais 
        Controller.loadVoters();
        Controller.loadProfessionals();

        
        
        Controller.startMenu();

    }
}
