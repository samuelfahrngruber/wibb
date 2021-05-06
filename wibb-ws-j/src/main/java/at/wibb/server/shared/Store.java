package at.wibb.server.shared;

import static at.wibb.server.shared.Preconditions.checkNotNull;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class Store {

    private final String name;
    private final Icon icon;

    public Store(@NonNull String name, @NonNull Icon icon) {
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
        Store store = (Store) o;
        return Objects.equals(name, store.name) && Objects.equals(icon, store.icon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, icon);
    }
}
