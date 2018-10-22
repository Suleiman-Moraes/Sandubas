//package br.com.sandubas.service;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.inject.Inject;
//import javax.persistence.EntityGraph;
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.FlushModeType;
//import javax.persistence.LockModeType;
//import javax.persistence.Query;
//import javax.persistence.StoredProcedureQuery;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaDelete;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.CriteriaUpdate;
//import javax.persistence.metamodel.Metamodel;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import br.com.sandubas.dao.TipoProdutoDAO;
//import br.com.sandubas.exception.NegocioException;
//import br.com.sandubas.model.TipoProduto;
//import junit.framework.TestCase;
//
//public class TipoProdutoServiceTest extends TestCase{
//
//	private TipoProduto objeto = new TipoProduto("Teste Inserção", "Teste");
//	
//	@Inject
//	private TipoProdutoService service;;
//	
////	@Before
////	public void inicialiaDependencias() {
////		service.setPersistencia(new TipoProdutoDAO());
////		service.getPersistencia().setEntityManager(new EntityManager);
////	}
//	
//	@Test
//	public void testContarRegistrosCadastrados() {
//		System.out.println();
//		int cont = service.contarRegistrosCadastrados("", "");
//		System.out.println(cont);
//		assertEquals(8, cont);
//	}
//
//	@Test
//	public void testSalvar() {
//		try {
//			String testeAlterar = "Teste alterar";
//			service.salvar(objeto);
//			assertTrue(objeto.getId() > 0);
//			objeto.setNome(testeAlterar);
//			service.salvar(objeto);
//			assertTrue(objeto.getId() > 0);
//			assertEquals(testeAlterar, objeto.getNome());
//		} catch (Exception e) {
//			assertTrue(Boolean.FALSE);
//		}
//	}
//
//	@Test
//	public void testExcluir() {
//		try {
//			service.excluir(objeto);
//			Long id = service.getPersistencia().findById(TipoProduto.class, objeto.getId()).getId();
//			Long esperado = 0l;
//			assertEquals(null, id);
//		} catch (NegocioException e) {
//			assertTrue(Boolean.FALSE);
//		}
//	}
//
//}
