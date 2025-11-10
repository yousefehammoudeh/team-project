package entity;

import java.util.Objects;
import java.util.UUID;

public class Participant {
    private final String id;
    private final String name;

    public Participant(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public Participant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Participant))
            return false;
        Participant that = (Participant) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Participant{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
    }
}
