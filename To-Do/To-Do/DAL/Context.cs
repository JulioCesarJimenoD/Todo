using Microsoft.EntityFrameworkCore;
using To_Do.Models;

namespace To_Do.DAL
{
    public class Context :DbContext
    {
        public DbSet<Agenda> Agendas { get; set; }


        public Context(DbContextOptions<Context> options)
            : base (options)
        {
             
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Agenda>(entity =>
            {
                entity.Property(e => e.Nombre)
               .HasMaxLength(30)
               .IsFixedLength();
            });

            modelBuilder.Entity<Agenda>(entity =>
            {
                entity.Property(e => e.Descripcion)
               .HasMaxLength(255)
               .IsFixedLength();
            });

            modelBuilder.Entity<Agenda>(entity =>
            {
                entity.Property(e => e.Prioridad)
               .HasMaxLength(10)
               .IsFixedLength();
            });

            OnModelCreatingPartial(modelBuilder);

            //modelBuilder.Entity<Agenda>().HasData(
            //    new Agenda() { 
            //        AgendaId = 3,
            //        Nombre = "Llamar a Raul",
            //        Descripcion = "Llamar a raul ante de las 12",
            //        Prioridad = "Alta"
            //    });

        }

        private void OnModelCreatingPartial(ModelBuilder modelBuilder)
        {
           
        }
    }
  
}
