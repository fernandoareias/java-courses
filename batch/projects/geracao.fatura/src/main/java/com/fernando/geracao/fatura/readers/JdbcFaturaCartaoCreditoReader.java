package com.fernando.geracao.fatura.readers;

import com.fernando.geracao.fatura.dominio.FaturaCartaoCredito;
import com.fernando.geracao.fatura.dominio.Transacao;
import org.springframework.batch.item.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


public class JdbcFaturaCartaoCreditoReader  implements ItemStreamReader<FaturaCartaoCredito> {

    private ItemStreamReader<Transacao> delegate;
    private Transacao transacaoAtual;

    public JdbcFaturaCartaoCreditoReader(ItemStreamReader<Transacao> delegate) {
        this.delegate = delegate;
    }

    @Override
    public FaturaCartaoCredito read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if(transacaoAtual == null)
            transacaoAtual = delegate.read();

        FaturaCartaoCredito faturaCartaoCredito = null;
        Transacao transacao = transacaoAtual;
        transacaoAtual = null;

        if(transacao != null){
            faturaCartaoCredito = new FaturaCartaoCredito();
            faturaCartaoCredito.setCartaoCredito(transacao.getCartaoCredito());
            faturaCartaoCredito.setCliente(transacao.getCartaoCredito().getCliente());
            faturaCartaoCredito.getTransacoes().add(transacao);

            while(isTransacaoRelacionada(transacao)){
                faturaCartaoCredito.getTransacoes().add(transacaoAtual);
            }
        }

        return  faturaCartaoCredito;
    }

    private boolean isTransacaoRelacionada(Transacao transacao) throws Exception {
        return peek() != null && transacao.getCartaoCredito().getNumeroCartaoCredito() == transacaoAtual.getCartaoCredito().getNumeroCartaoCredito();
    }

    private Transacao peek() throws Exception {
        transacaoAtual = delegate.read();
        return transacaoAtual;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        delegate.open(executionContext);
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        delegate.update(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        delegate.close();
    }
}
