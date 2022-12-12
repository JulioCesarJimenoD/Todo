using System.ComponentModel.DataAnnotations;

namespace To_Do.Models
{
    public class Agenda
    {
        [Key]
        public int AgendaId { get; set; }
        public string Nombre { get; set; }
        public string Descripcion { get; set; }
        public string Prioridad { get; set; }

    }
}
