package io.github.fernando.domain.repositories;

import io.github.fernando.domain.entities.Cliente;
//import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.List;

//@Repository
public interface Clientes extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByNameLike(String nome);
    List<Cliente> findByNomeLikeOrId(String nome, Integer id);
    Cliente findOneByNome(String nome);

    @Query(value = "select c from Cliente where c.nome like '%:nome%' limit 1", nativeQuery = true)
    Cliente encontrarPorNome(@Param("nome") String nome);

    @Query("delete from Cliente c where c.nome = :nome")
    @Modifying
    void deleteByNome(String nome);

    @Query(" select c from Cliente c left join fetch c.pedidos where c.id = :id")
    Cliente findClienteFetchPedidos(@Param("id") Integer id);


////    private static String INSERT = "insert into cliente (nome) values (?)";
////    private static String SELECT_ALL = "select * from cliente";
////    private static String UPDATE = "update cliente set nome = ? where id = ?";
////    private static String DELETE = "delete from cliente where id = ?";
//
////    @Autowired
////    private JdbcTemplate _context;
////
//    @Autowired
//    private EntityManager _context;
//
//
//    @Transactional
//    public Cliente salvar(Cliente cliente){
////        _context.update(INSERT, new Object[]{ cliente.getName()});
//
//        _context.persist(cliente);
//
//        return cliente;
//    }
//
//
//    @Transactional
//    public Cliente atualizar(Cliente cliente){
////        _context.update(UPDATE, new Object[]{ cliente.getName(), cliente.getId()});
//
//        _context.merge(cliente);
//
//        return cliente;
//    }
//
//    public void deletar(Cliente cliente)
//    {
//        if(!_context.contains(cliente))
//            cliente = _context.merge(cliente);
//
//        _context.remove(cliente);
//    }
//
//    public void deletar(Integer id)
//    {
//
//        var cliente = _context.find(Cliente.class, id);
//        deletar(cliente);
//
////        _context.update(DELETE, new Object[]{id});
//
//
//    }
//
//   @Transactional(readOnly = true)
//    public List<Cliente> buscarPorNome(String nome)
//   {
//
//       String sql = "select c from Cliente c where c.nome like :nome";
//       var query = _context.createQuery(sql, Cliente.class);
//       query.setParameter("nome", "%" + nome + "%");
//       return query.getResultList();
//
////        return _context.query(
////                SELECT_ALL.concat("where name like ?"),
////                new Object[]{ "%" + nome + "%" }
////                ,obterClienteMapper()
////        );
//
//
//    }
//
//    @Transactional(readOnly = true)
//    public List<Cliente> obterTodos()
//    {
//        return _context
//                .createQuery("from Cliente", Cliente.class)
//                .getResultList();
//
////        return _context.query(SELECT_ALL, obterClienteMapper());
//
//    }
//
//    private static RowMapper<Cliente> obterClienteMapper() {
//
////        return new RowMapper<Cliente>() {
////            @Override
////            public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
////                return new Cliente(rs.getString("nome"));
////            }
////        };


//    }
}
