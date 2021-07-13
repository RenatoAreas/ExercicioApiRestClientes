package br.com.renato.interfaces;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.renato.entities.Cliente;

public interface IClienteRepository extends CrudRepository<Cliente, Integer>{
	
	@Query("from Cliente c where c.cpf = :param")
	Cliente findByCpf(@Param("param") String cpf);
	

}

