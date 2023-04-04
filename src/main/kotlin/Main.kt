import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.io.FileReader

fun main(args: Array<String>) {
//    3) Dado um vetor que guarda o valor de faturamento diário de uma distribuidora, faça um programa, na linguagem que desejar, que calcule e retorne:
//    • O menor valor de faturamento ocorrido em um dia do mês;
//    • O maior valor de faturamento ocorrido em um dia do mês;
//    • Número de dias no mês em que o valor de faturamento diário foi superior à média mensal.



    // Get the data
    val gson = Gson()
    val dailyEarningType = object : TypeToken<Array<DailyEarning>>() {}.type
    val dataSet: Array<DailyEarning> = gson.fromJson(FileReader("src/main/resources/dados.json"), dailyEarningType)

    val menorFat = obterMenorFat(dataSet)
    val maiorFat = obterMaiorFat(dataSet)
    val diasAcimaDaMedia = obterDiasAcimaDaMedia(dataSet)

    println("O MENOR FATURAMENTO ocorreu no dia ${menorFat.dia}, cujo valor foi: R$ ${menorFat.valor}")
    println("O MAIOR FATURAMENTO ocorreu no dia ${maiorFat.dia}, cujo valor foi: R$ ${maiorFat.valor}")
    println("Houveram $diasAcimaDaMedia dias em que o faturamento diário foi superior à média mensal.")


}

val SEM_FATURAMENTO_ERRO = Throwable("Não houve renda em nenhuma das datas.")

fun obterMenorFat(dataSet: Array<DailyEarning>) : DailyEarning {
    for (data in dataSet.sortedBy { it.valor }) {
        if (data.valor > 0) {
            return data
        }
    }
    throw (SEM_FATURAMENTO_ERRO)
}

fun obterMaiorFat(dataSet: Array<DailyEarning>) : DailyEarning {
    if (dataSet.sortedBy { it.valor }.last().valor > 0) {
        return dataSet.sortedBy { it.valor }.last()
    }
    throw (SEM_FATURAMENTO_ERRO)
}

fun obterMedia(dataSet: Array<DailyEarning>) : Double {
    var acumulado = 0.0
    var diasLaborais = 0

    for (data in dataSet) {
        if (data.valor > 0)  {
            acumulado += data.valor
            diasLaborais++
        }
    }
    return acumulado / diasLaborais
}

fun obterDiasAcimaDaMedia(dataSet: Array<DailyEarning>) : Int {
    val media = obterMedia(dataSet)
    var diasAcimaDaMedia = 0
    for (data in dataSet) {
        if (data.valor > media) {
            diasAcimaDaMedia++
        }
    }
    return diasAcimaDaMedia
}


data class DailyEarning(
    val dia: Int,
    val valor: Double
)