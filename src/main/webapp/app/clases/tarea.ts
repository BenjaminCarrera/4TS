export interface Tarea {
  id: number;
  descripcion: string;
  titulo: string;
  usuario_creador_id: number;
  usuario_ejecutor_id: number;
  requerimiento_id: number;
  candidato_id: number;
  estatus_tarea_id: number;
  tipo_tarea_id: number;
}
