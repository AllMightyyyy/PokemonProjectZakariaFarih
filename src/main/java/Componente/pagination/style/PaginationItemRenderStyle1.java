package Componente.pagination.style;

import Componente.pagination.DefaultPaginationItemRender;

import javax.swing.*;

public class PaginationItemRenderStyle1 extends DefaultPaginationItemRender {

    @Override
    public JButton createButton(Object value, boolean isPrevious, boolean isNext, boolean enable) {
        JButton button = super.createButton(value, isPrevious, isNext, enable);
        button.setUI(new ButtonUI());
        return button;
    }

    @Override
    public Object createPreviousIcon() {
        return "Anterior";
    }

    @Override
    public Object createNextIcon() {
        return "Siguiente";
    }
}
