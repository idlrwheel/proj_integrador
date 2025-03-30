@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @GetMapping("/{codigo}")
    public Produto buscarProdutoPorId(@PathVariable int codigo) {
        return produtoService.buscarProdutoPorId(codigo);
    }
}


