// Gerencia o processo eleitoral
public class CertifiedProfessional {

    private String user; 
    private String password; 

    public void startSession(Eleicao election, String password) {
        election.start(password);
    }

    public void endSession(Eleicao election, String password) {
        election.finish(password);
    }

    public static class Builder {
        protected String user;
        protected String password;

        public Builder user(String user) {
            this.user = user;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public CertifiedProfessional build() {
            if (user == null)
                throw new IllegalArgumentException("user mustn't be null");

            if (user.isEmpty())
                throw new IllegalArgumentException("user mustn't be empty");

            if (password == null)
                throw new IllegalArgumentException("password mustn't be null");

            if (password.isEmpty())
                throw new IllegalArgumentException("password mustn't be empty");

            return new CertifiedProfessional(
                    this.user,
                    this.password);
        }
    }

    protected CertifiedProfessional(
            String user,
            String password) {
        this.user = user; 
        this.password = password; 
    }

    public String getUser() {
        return this.user;
    }

    public static void tseMenu() {
      try {
        TSEProfessional tseProfessional = getTSEProfessional();
        if (tseProfessional == null)
          return;
        print("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        boolean back = false;
        while (!back) {
          print("Escolha uma opção:");
          if (tseProfessional instanceof TSEEmployee) {
            print("(1) Cadastrar candidato");
            print("(2) Remover candidato");
            print("(0) Sair");
            int command = readInt();
            switch (command) {
              case 1 -> addCandidate((TSEEmployee) tseProfessional);
              case 2 -> removeCandidate((TSEEmployee) tseProfessional);
              case 0 -> back = true;
              default -> print("Comando inválido\n");
            }
          } else if (tseProfessional instanceof CertifiedProfessional) {
            print("(1) Iniciar sessão");
            print("(2) Finalizar sessão");
            print("(3) Mostrar resultados");
            print("(0) Sair");
            int command = readInt();
            switch (command) {
              case 1 -> startSession((CertifiedProfessional) tseProfessional);
              case 2 -> endSession((CertifiedProfessional) tseProfessional);
              case 3 -> showResults((CertifiedProfessional) tseProfessional);
              case 0 -> back = true;
              default -> print("Comando inválido\n");
            }
          }
        }
      } catch (Warning e) {
        print(e.getMessage());
      } catch (Exception e) {
        print("Ocorreu um erro inesperado");
      }
    }
}
