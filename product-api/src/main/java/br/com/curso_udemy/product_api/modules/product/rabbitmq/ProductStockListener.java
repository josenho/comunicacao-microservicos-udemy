package br.com.curso_udemy.product_api.modules.product.rabbitmq;

import br.com.curso_udemy.product_api.modules.product.dto.ProductStockDTO;
import br.com.curso_udemy.product_api.modules.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class ProductStockListener {

    @Autowired
    private ProductService productService;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @RabbitListener(queues = "${app-config.rabbit.queue.product-stock}")
    //recebe a mensagem de atualização do estoque
    public void recieveProductStockMessage(String messageBody){
        try {
            log.info("Recebendo mensagem bruta do RabbitMQ: {}", messageBody);

            //Converte manualmente o texto JSON para DTO
            ProductStockDTO product = MAPPER.readValue(messageBody, ProductStockDTO.class);

            log.info("Mensagem convertida com sucesso: {}", product);
            productService.updateProductStock(product);
        } catch (Exception e) {
            log.error("Erro ao tentar converter o JSON da fila", e);
        }
    }
}
