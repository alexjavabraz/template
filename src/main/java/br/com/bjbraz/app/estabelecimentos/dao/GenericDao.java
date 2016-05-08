package br.com.bjbraz.app.estabelecimentos.dao;

import java.util.List;

/**
 * @author Alex Simas Braz
 * @param <T>
 */
public interface GenericDao<T> {

    void incluir(T item);

    void excluir(T item);

    void atualizar(T item);

    /**
     * Lista os itens. quantidade máxima definida por ipm.quantidade.pagina no arquivo
     * paginacao.properties
     * @return
     */
    List<T> listar();

    /**
     * Deve ser implementado pela subclasse, se for chamado. Retorna null na implementacao
     * GenericDaoImpl.
     * @param item
     * @return
     */
    List<T> listar(T item);

    /**
     * Lista todos itens, deve ser usado somente para preencher combobox
     * @return
     */
    List<T> listarTodos();

    List<T> listarForName(final String description);

    /**
     * Busca o objeto no banco, procurando por seu Id.
     * @param item
     * @return Objeto encontrado
     * @throws DaoException se ID nao foi setado 
     */
    T buscar(T item);

    /**Busca o objeto no banco, procurando por seu Id.
     * @param item
     * @return Objeto encontrado ou null se ID nao foi setado
     */
    T buscarReturningNullIdEmpty(T item);

}
