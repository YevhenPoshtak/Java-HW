package task4_4.criterion;

import task4_4.equation.QuadraticEquation;

/**
 * Selection criterion for equations. An interface rather than a base
 * class because criteria share behaviour only, not data.
 */
public interface Criterion {

    /**
     * The check belongs to the criterion: the list does not have to
     * know the selection rules.
     */
    boolean matches(QuadraticEquation equation);
}
