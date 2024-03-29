package Componente.pagination;

import java.util.List;

/**
 * The type Page.
 */
public class Page {

    /**
     * Gets current.
     *
     * @return the current
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Sets current.
     *
     * @param current the current
     */
    public void setCurrent(int current) {
        this.current = current;
    }

    /**
     * Is previous boolean.
     *
     * @return the boolean
     */
    public boolean isPrevious() {
        return previous;
    }

    /**
     * Sets previous.
     *
     * @param previous the previous
     */
    public void setPrevious(boolean previous) {
        this.previous = previous;
    }

    /**
     * Is next boolean.
     *
     * @return the boolean
     */
    public boolean isNext() {
        return next;
    }

    /**
     * Sets next.
     *
     * @param next the next
     */
    public void setNext(boolean next) {
        this.next = next;
    }

    /**
     * Gets items.
     *
     * @return the items
     */
    public List<Object> getItems() {
        return items;
    }

    /**
     * Sets items.
     *
     * @param items the items
     */
    public void setItems(List<Object> items) {
        this.items = items;
    }

    /**
     * Gets total page.
     *
     * @return the total page
     */
    public int getTotalPage() {
        return totalPage;
    }

    /**
     * Sets total page.
     *
     * @param totalPage the total page
     */
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * Instantiates a new Page.
     *
     * @param current   the current
     * @param previous  the previous
     * @param next      the next
     * @param items     the items
     * @param totalPage the total page
     */
    public Page(int current, boolean previous, boolean next, List<Object> items, int totalPage) {
        this.current = current;
        this.previous = previous;
        this.next = next;
        this.items = items;
        this.totalPage = totalPage;
    }

    /**
     * Instantiates a new Page.
     */
    public Page() {
    }

    private int current;
    private boolean previous;
    private boolean next;
    private List<Object> items;
    private int totalPage;

    @Override
    public String toString() {
        return "current: " + current + "\n" + previous + " " + items.toString() + " " + next;
    }

    /**
     * The type Break label.
     */
    public static class BreakLabel {

        /**
         * Gets page.
         *
         * @return the page
         */
        public int getPage() {
            return page;
        }

        /**
         * Sets page.
         *
         * @param page the page
         */
        public void setPage(int page) {
            this.page = page;
        }

        /**
         * Instantiates a new Break label.
         *
         * @param page the page
         */
        public BreakLabel(int page) {
            this.page = page;
        }

        /**
         * Instantiates a new Break label.
         */
        public BreakLabel() {
        }

        private int page;

        @Override
        public String toString() {
            return "...";
        }
    }
}
