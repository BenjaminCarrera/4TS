/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { TipoTareaDetailComponent } from 'app/entities/tipo-tarea/tipo-tarea-detail.component';
import { TipoTarea } from 'app/shared/model/tipo-tarea.model';

describe('Component Tests', () => {
  describe('TipoTarea Management Detail Component', () => {
    let comp: TipoTareaDetailComponent;
    let fixture: ComponentFixture<TipoTareaDetailComponent>;
    const route = ({ data: of({ tipoTarea: new TipoTarea(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoTareaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoTareaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoTareaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoTarea).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
