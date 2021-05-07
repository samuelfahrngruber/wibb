package at.wibb.server.shared;

import static at.wibb.server.shared.Preconditions.checkArgument;
import static at.wibb.server.shared.Preconditions.checkNotNull;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class Icon {
    
    private final String imgUrl;
    private final String backgroundColor;

    public Icon(@NonNull String imgUrl, @NonNull String backgroundColor) {
        this.imgUrl = checkNotNull(imgUrl);
        this.backgroundColor = checkNotNull(backgroundColor);
        checkArgument(isValidColor(backgroundColor));
    }

    @NonNull
    public String getImgUrl() {
        return imgUrl;
    }

    @NonNull
    public String getBackgroundColor() {
        return backgroundColor;
    }

    private static boolean isValidColor(String hexColorCode) {
        return hexColorCode.startsWith("#") && hexColorCode.length() == 7;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Icon icon = (Icon) o;
        return Objects.equals(imgUrl, icon.imgUrl) && Objects.equals(backgroundColor, icon.backgroundColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imgUrl, backgroundColor);
    }
}
