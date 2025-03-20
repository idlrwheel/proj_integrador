import jakarta.persistence.*;
import java.math.BigDecimal.*;


@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    private String nome;
    private BigDecimal valorProduto;
    private String descricaoDetalhada;
    private Integer qtdEstoque;
    private String status;

    @Column(precision = 2, scale = 1)
    private BigDecimal avaliacao;


    public Produto(BigDecimal avaliacao, Integer codigo, String descricaoDetalhada, String nome, Integer qtdEstoque, StatusProduto status, BigDecimal valorProduto) {
        this.avaliacao = avaliacao;
        this.codigo = codigo;
        this.descricaoDetalhada = descricaoDetalhada;
        this.nome = nome;
        this.qtdEstoque = qtdEstoque;
        this.status = status;
        this.valorProduto = valorProduto;
    }


}
