package br.com.renato.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.renato.dtos.ClienteGetDTO;
import br.com.renato.dtos.ClientesPostDTO;
import br.com.renato.dtos.ClientesPutDTO;
import br.com.renato.entities.Cliente;
import br.com.renato.interfaces.IClienteRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Controller
@Transactional
public class ClientesController {

	private static final String ENDPOINT = "api/clientes";

	@Autowired
	private IClienteRepository clienteRepository;

	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	@RequestBody
	public ResponseEntity<String> post(@RequestBody ClientesPostDTO dto) {

		try {
			if (clienteRepository.findByCpf(dto.getCpf()) != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF já cadastrado");
			}
			// criando o usuário e recuperando os dados do DTO
			Cliente cliente = new Cliente();
			cliente.setNome(dto.getNome());
			cliente.setCpf(dto.getCpf());
			cliente.setEmail(dto.getEmail());

			clienteRepository.save(cliente);

			return ResponseEntity.status(HttpStatus.OK).body("Cliente cadastrado com sucesso !");
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	@RequestBody
	public ResponseEntity<String> put(@RequestBody ClientesPutDTO dto) {

		Optional<Cliente> result = clienteRepository.findById(dto.getIdCliente());
		try {
			if (result == null) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("CLiente não encontrado!");
			}
			Cliente cliente = new Cliente();
			cliente.setNome(dto.getNome());
			cliente.setEmail(dto.getEmail());

			clienteRepository.save(cliente);

			return ResponseEntity.status(HttpStatus.OK).body("Cliente atualizado com sucesso!");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error" + e.getMessage());
		}
	}

	@CrossOrigin
	@RequestMapping(value = ENDPOINT + "/{idCliente}", method = RequestMethod.DELETE)
	@RequestBody
	public ResponseEntity<String> delete(@PathVariable("idCliente") Integer idCliente) {

		try {

			Optional<Cliente> result = clienteRepository.findById(idCliente);

			if (result == null) {
				return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Cliente não encontrado!");
			}
			Cliente cliente = result.get();

			clienteRepository.delete(cliente);

			return ResponseEntity.status(HttpStatus.OK).body("Cliente excluido com sucesso!");
		}

		catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error" + e.getMessage());
		}

	}

	@CrossOrigin
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	@RequestBody
	public ResponseEntity<List<ClienteGetDTO>> get() {
		try {
			List<ClienteGetDTO> result = new ArrayList<ClienteGetDTO>();

			for (Cliente cliente : clienteRepository.findAll()) {
				ClienteGetDTO dto = new ClienteGetDTO();

				dto.setIdCliente(cliente.getIdCliente());
				dto.setNome(cliente.getNome());
				dto.setEmail(cliente.getEmail());
				dto.setCpf(cliente.getCpf());

				result.add(dto);
			}
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

	}
}
