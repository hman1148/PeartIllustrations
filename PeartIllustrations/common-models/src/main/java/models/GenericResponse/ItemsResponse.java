package models.GenericResponse;

import java.util.List;

public class ItemsResponse<T> extends Response<T> {
    private List<T> items;

    public ItemsResponse(List<T> items, String message, boolean success) {
        super(message, success);
        this.items = items;
    }

    public Iterable<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
