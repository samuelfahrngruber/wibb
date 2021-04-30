package at.wibb.server.shared;

import static at.wibb.server.shared.Preconditions.checkNotNull;

import org.springframework.lang.NonNull;

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

}
