@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll(); 
    }

    public Produto buscarProdutoPorId(int codigo) {
        return produtoRepository.findById(codigo).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
}

