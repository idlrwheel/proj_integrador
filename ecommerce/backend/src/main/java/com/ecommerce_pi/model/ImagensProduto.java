import jakarta.persistence.*;


@Entity
public class ImagensProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private String nomeArquivo;
    private String diretorioOrigem;
    private Boolean principal;

}
