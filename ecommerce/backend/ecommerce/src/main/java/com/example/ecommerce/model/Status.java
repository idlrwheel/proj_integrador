
@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;

    private String nome;
    private BigDecimal avaliacao; 
    private String descricaoDetalhada;
    private int qtdEstoque;
    private BigDecimal valorProduto; 

    @Enumerated(EnumType.STRING)
    private Status status; 

    @OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
    private List<ImagemProduto> imagens;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(BigDecimal avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getDescricaoDetalhada() {
        return descricaoDetalhada;
    }

    public void setDescricaoDetalhada(String descricaoDetalhada) {
        this.descricaoDetalhada = descricaoDetalhada;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public BigDecimal getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(BigDecimal valorProduto) {
        this.valorProduto = valorProduto;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<ImagemProduto> getImagens() {
        return imagens;
    }

    public void setImagens(List<ImagemProduto> imagens) {
        this.imagens = imagens;
    } 

    
}

public enum Status {
    ATIVO, DESATIVADO;
}
