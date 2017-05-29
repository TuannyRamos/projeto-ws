package br.com.projetows.tempo;

import br.com.projetows.models.Cidade;
import br.com.projetows.models.Previsao;
import java.net.URL;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 *
 * @author Tuanny
 */
@WebService(serviceName = "PrevisaoDoTempo", targetNamespace = "http://suaempresa.com/")
@Stateless()
public class PrevisaoDoTempo {

    @WebMethod(operationName = "buscarCidades")
    public List<Cidade> buscarCidades(@WebParam(name = "nomeCidade") String nomeCidade) {
        String url = "http://servicos.cptec.inpe.br/XML/listaCidades?city=" + cidadeFormatada(nomeCidade);
        return lerCidades(url);
    }

    @WebMethod(operationName = "obterPrevisao")
    public Cidade obterPrevisao(@WebParam(name = "idCidade") int idCidade) {
        String url = "http://servicos.cptec.inpe.br/XML/cidade/" + idCidade + "/previsao.xml";
        return lerPrevisao(url);
    }

    private String cidadeFormatada(String nomeCidade) {
        String strSemAcentos = Normalizer.normalize(nomeCidade, Normalizer.Form.NFD);
        strSemAcentos = strSemAcentos.replaceAll("[^\\p{ASCII}]", "");
        return strSemAcentos.replaceAll(" ", "%20");
    }

    private List<Cidade> lerCidades(String url) {
        List<Cidade> cidadeList = new ArrayList();

        try {
            Document doc = readXml(url);
            NodeList nList = doc.getElementsByTagName("cidade");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Cidade cidade = new Cidade();
                    cidade.setId(Integer.valueOf(eElement.getElementsByTagName("id").item(0).getTextContent()));
                    cidade.setNome(eElement.getElementsByTagName("nome").item(0).getTextContent());
                    cidade.setUf(eElement.getElementsByTagName("uf").item(0).getTextContent());
                    cidadeList.add(cidade);
                }
            }
        } catch (Exception e) {
        }
        return cidadeList;
    }

    private Cidade lerPrevisao(String url) {
        Cidade cidade = new Cidade();

        try {
            Document doc = readXml(url);
            NodeList nListCidade = doc.getElementsByTagName("cidade");
            if (nListCidade.getLength() > 0) {
                Node nCidade = nListCidade.item(0);
                if (nCidade.getNodeType() == Node.ELEMENT_NODE) {
                    Element eCidade = (Element) nCidade;
                    cidade.setNome(eCidade.getElementsByTagName("nome").item(0).getTextContent());
                    cidade.setUf(eCidade.getElementsByTagName("uf").item(0).getTextContent());
                    cidade.setAtualizacao(formatarData(eCidade.getElementsByTagName("atualizacao").item(0).getTextContent()));

                    NodeList nListPrevisao = eCidade.getElementsByTagName("previsao");
                    for (int i = 0; i < nListPrevisao.getLength(); i++) {
                        Node nPrevisao = nListPrevisao.item(i);
                        if (nPrevisao.getNodeType() == Node.ELEMENT_NODE) {
                            Element ePrevisao = (Element) nPrevisao;
                            Previsao previsao = new Previsao();
                            previsao.setDia(formatarData(ePrevisao.getElementsByTagName("dia").item(0).getTextContent()));
                            previsao.setTempo(ePrevisao.getElementsByTagName("tempo").item(0).getTextContent());
                            previsao.setMaxima(Integer.valueOf(ePrevisao.getElementsByTagName("maxima").item(0).getTextContent()));
                            previsao.setMinima(Integer.valueOf(ePrevisao.getElementsByTagName("minima").item(0).getTextContent()));
                            previsao.setIuv(Double.valueOf(ePrevisao.getElementsByTagName("iuv").item(0).getTextContent()));
                            cidade.getPrevisoes().add(previsao);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return cidade;
    }

    private Document readXml(String url) throws Exception {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(new URL(url).openStream());
        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();
        return doc;
    }

    private Date formatarData(String param) throws Exception {
        return new SimpleDateFormat("yyyy-MM-dd").parse(param);
    }
}
