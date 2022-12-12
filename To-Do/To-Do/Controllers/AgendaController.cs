using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Drawing;
using To_Do.DAL;
using To_Do.Models;

namespace To_Do.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class AgendaController : ControllerBase
    {
        private readonly Context _context;

        public AgendaController(Context context)
        {
            _context = context;
        }  

        [HttpGet("GetAgendas")]
        public async Task<ActionResult<IEnumerable<Agenda>>> GetAgendas()
        {
            return await _context.Agendas.ToListAsync();
        }

        [HttpGet("GetAgendas{id}")]

        public async Task<ActionResult<Agenda>> GetAgenda(int id)
        {
            var agenda = await _context.Agendas.FindAsync(id);

            if(agenda == null)
            {
                return NotFound();
            }
            return agenda;
        }


        [HttpGet("PutAgendas{id}")]
        public async Task<IActionResult> PutAgenda(int id, Agenda agenda)
        {
            if(id != agenda.AgendaId)
            {
                return BadRequest();
            }
            _context.Entry(agenda).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch(DbUpdateConcurrencyException)
            {
                if (!AgendaExiste(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }
       
            return NoContent();
        }

        [HttpPost("PostAgendas")]
        public async Task<ActionResult<Agenda>> PostAgenda(Agenda agenda)
        {
            _context.Agendas.Add(agenda);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetBarbero", new { id = agenda.AgendaId }, agenda);
        }


        [HttpDelete("Delete{id}")]
        public async Task<IActionResult> DeleteAgenda(int id)
        {
            var agenda = await _context.Agendas.FindAsync(id);
            if (agenda == null)
            {
                return NotFound();
            }

            _context.Agendas.Remove(agenda);
            await _context.SaveChangesAsync();

            return NoContent();
        }
        private bool AgendaExiste(int id)
        {
            return _context.Agendas.Any(e => e.AgendaId == id);
        }
    }
}
