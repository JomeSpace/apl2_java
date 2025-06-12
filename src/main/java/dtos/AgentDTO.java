package dtos;

/**
 * Data Transfer Object used to exchange Agent Data between UI and the Simulation
 *
 * @param id
 * @param value
 * @param limit
 * @param status
 */
public record AgentDTO(String id, double value, double limit, boolean status) {

    /**
     * @return overview of the AgentDTO object a String
     */
    @Override
    public String toString() {
        return "AgentDTO {" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", limit=" + limit +
                ", status=" + status +
                '}';
    }
}
