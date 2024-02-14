enum class Nivel { BASICO, INTERMEDIARIO, AVANCADO }

data class Usuario(val nome: String, val id: Int) {
     init {
        require(nome.isNotBlank()) { "Preencha o nome do usuário" }
        require(id > 0) { "Id do usuário deve ser positivo e maior que zero." }
    }
}

data class ConteudoEducacional(val nome: String, val duracao: Int = 60, val nivel: Nivel) {
      init {
        require(duracao > 0) { "Informe a duração correta do curso" }
    }
}

data class Formacao(val nome: String, val conteudos: List<ConteudoEducacional>) {
    private val inscritos = mutableListOf<Usuario>()
    
    val totalDuracao by lazy { conteudos.sumOf { it.duracao } }

    fun matricular(usuario: Usuario) {       
        require(!inscritos.any { it.id == usuario.id }) { "Usuário com id ${usuario.id} já está matriculado." }        
        inscritos.add(usuario)        
    }
    
    fun getAllFormacaoData() {
        println("Formação : $nome\n") 
        
        println("Grade Curricular:")
        
        conteudos.forEach { item -> 
            println("${item.nome} | Duração de ${item.duracao} horas | Nível: ${item.nivel} ")
        }
        
       println("\nTotal de Duração: ${this.totalDuracao} horas")           
        
       println("\nAlunos Matriculados \n") 
       
       inscritos.forEach { item -> 
            println("Nome : ${item.nome}, Cadastro : ${item.id}")
            
        }
        
    }
}

fun main() {  	
   try {
       val conteudo = mutableListOf<ConteudoEducacional>(
        ConteudoEducacional(nome = "Princípios de Engenharia de Software", duracao = 50, Nivel.BASICO),
        ConteudoEducacional(nome = "Java", duracao = 40, Nivel.INTERMEDIARIO),
        ConteudoEducacional(nome = "Kotlin", duracao = 100, Nivel.AVANCADO),
        //ConteudoEducacional(nome = "Desenvolvimento Ágil", duracao = -20, Nivel.BASICO), //Testes 
		)       

       val formacao = Formacao(nome="Engenharia de Software", conteudo).apply {
           listOf(
                Usuario(nome = "Giovanni Donati", id = 1),
                Usuario(nome = "Kemili Freitas Donati", id = 2),
                //Usuario(nome = "Salvatore Freitas Donati", id = 2), //Teste
                Usuario(nome = "Malia Freitas Donati", id = 3)
           ).forEach(this::matricular)   
           
       }                  
       
       formacao.getAllFormacaoData() 
       
    } catch (e: Exception) {
        println("Erro inesperado: ${e.message}")
    }
}

