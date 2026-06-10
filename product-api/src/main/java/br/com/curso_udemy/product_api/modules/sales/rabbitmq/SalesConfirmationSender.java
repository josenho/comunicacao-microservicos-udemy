package br.com.curso_udemy.product_api.modules.sales.rabbitmq;

import br.com.curso_udemy.product_api.modules.sales.dto.SalesConfirmationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class SalesConfirmationSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${app-config.rabbit.exchange.product}")
    private String productTopicExchange;

    @Value("${app-config.rabbit.routingKey.sales-confirmation}")
    private String salesConfirmationKey;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void sendSalesConfirmationMessage(SalesConfirmationDTO message) {
        try {
            log.info("Preparando envio da confirmação de venda: {}", message);

            // Convertemos o DTO para uma String JSON pura!
            String jsonMessage = MAPPER.writeValueAsString(message);

            // Enviamos a String! O RabbitMQ aceita texto puro sem reclamar de versões
            rabbitTemplate.convertAndSend(productTopicExchange, salesConfirmationKey, jsonMessage);

            log.info("Mensagem enviada com sucesso para o RabbitMQ.");
        } catch (Exception e) {
            log.error("Erro fatal ao tentar enviar mensagem de confirmação de venda. ", e);
        }
    }
}
