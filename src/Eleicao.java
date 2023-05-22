

public class Eleicao {

    protected final String password;
    protected boolean status;
    protected String type; // Univeritaria | Presidencial | Estadual | Municipal


    public static class Builder {
        protected String password;
        protected String type; 

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder type(String type){
            this.type = type; 
            return this;
        }

        public Eleicao build() {
            if (password == null)
                throw new IllegalArgumentException("password mustn't be null");

            if (password.isEmpty())
                throw new IllegalArgumentException("password mustn't be empty");

            switch(type){
                case "Presidencial":    
                    return new EleicaoPresidencial(password); 
                default: 
                    throw new IllegalArgumentException("Invalid type of Election"); 
            }

        }
    }

    protected Eleicao(String password) {
        this.password = password;
        this.status = false;
    }

    protected Boolean isValid(String password) {
        return this.password.equals(password);
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

    public void getResults(String password) {
        if (!isValid(password))
          throw new Warning("Senha inválida");
    
        if (this.status)
          throw new StopTrap("Eleição ainda não finalizou, não é possível gerar o resultado");
    
        var builder = new StringBuilder();
    
        builder.append("Resultado da eleicao:\n");
    
    }
}
