﻿// <auto-generated />
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using To_Do.DAL;

#nullable disable

namespace ToDo.Migrations
{
    [DbContext(typeof(Context))]
    partial class ContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder.HasAnnotation("ProductVersion", "7.0.0");

            modelBuilder.Entity("To_Do.Models.Agenda", b =>
                {
                    b.Property<int>("AgendaId")
                        .ValueGeneratedOnAdd()
                        .HasColumnType("INTEGER");

                    b.Property<string>("Descripcion")
                        .IsRequired()
                        .HasMaxLength(255)
                        .HasColumnType("TEXT")
                        .IsFixedLength();

                    b.Property<string>("Nombre")
                        .IsRequired()
                        .HasMaxLength(30)
                        .HasColumnType("TEXT")
                        .IsFixedLength();

                    b.Property<string>("Prioridad")
                        .IsRequired()
                        .HasMaxLength(10)
                        .HasColumnType("TEXT")
                        .IsFixedLength();

                    b.HasKey("AgendaId");

                    b.ToTable("Agendas");
                });
#pragma warning restore 612, 618
        }
    }
}