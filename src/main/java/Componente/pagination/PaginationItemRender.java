package Componente.pagination;

import javax.swing.*;

/**
 * The interface Pagination item render.
 */
public interface PaginationItemRender {

    /**
     * Create pagination item j button.
     *
     * @param value      the value
     * @param isPrevious the is previous
     * @param isNext     the is next
     * @param enable     the enable
     * @return the j button
     */
    JButton createPaginationItem(Object value, boolean isPrevious, boolean isNext, boolean enable);

    /**
     * Create button j button.
     *
     * @param value      the value
     * @param isPrevious the is previous
     * @param isNext     the is next
     * @param enable     the enable
     * @return the j button
     */
    JButton createButton(Object value, boolean isPrevious, boolean isNext, boolean enable);

    /**
     * Create previous icon object.
     *
     * @return the object
     */
    Object createPreviousIcon();

    /**
     * Create next icon object.
     *
     * @return the object
     */
    Object createNextIcon();
}
