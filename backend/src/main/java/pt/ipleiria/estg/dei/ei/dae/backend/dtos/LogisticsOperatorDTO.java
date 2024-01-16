package pt.ipleiria.estg.dei.ei.dae.backend.dtos;


import pt.ipleiria.estg.dei.ei.dae.backend.entities.LogisticsOperator;

import java.util.List;

public class LogisticsOperatorDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private String role;

    public LogisticsOperatorDTO() {
    }

    public LogisticsOperatorDTO(String username, String password, String name, String email, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public static List<LogisticsOperatorDTO> toDTOs(List<LogisticsOperator> all) {
        return all.stream().map(LogisticsOperatorDTO::toDTO).collect(java.util.stream.Collectors.toList());
    }

    public static LogisticsOperatorDTO toDTO(LogisticsOperator logisticsOperator) {
        return new LogisticsOperatorDTO(
                logisticsOperator.getUsername(),
                logisticsOperator.getPassword(),
                logisticsOperator.getName(),
                logisticsOperator.getEmail(),
                logisticsOperator.getRole()
        );
    }
}
