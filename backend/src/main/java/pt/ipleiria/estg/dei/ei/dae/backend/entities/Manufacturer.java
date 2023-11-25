package pt.ipleiria.estg.dei.ei.dae.backend.entities;

public class Manufacturer extends User{
    public Manufacturer() {
    }

    public Manufacturer(String username, String password, String name, String email, String role) {
        super(username, password, name, email, role);
    }
}
