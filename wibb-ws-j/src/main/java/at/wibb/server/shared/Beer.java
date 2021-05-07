package at.wibb.server.shared;

import static at.wibb.server.shared.Preconditions.checkNotNull;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class Beer {
    
    private final String name;
    private final Icon icon;

    public Beer(@NonNull String name, @NonNull Icon icon) {
        this.name = checkNotNull(name);
        this.icon = checkNotNull(icon);
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Icon getIcon() {
        return icon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return Objects.equals(name, beer.name) && Objects.equals(icon, beer.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, icon);
    }
}
