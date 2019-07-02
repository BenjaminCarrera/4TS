/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstatusTareaDetailComponent } from 'app/entities/estatus-tarea/estatus-tarea-detail.component';
import { EstatusTarea } from 'app/shared/model/estatus-tarea.model';

describe('Component Tests', () => {
  describe('EstatusTarea Management Detail Component', () => {
    let comp: EstatusTareaDetailComponent;
    let fixture: ComponentFixture<EstatusTareaDetailComponent>;
    const route = ({ data: of({ estatusTarea: new EstatusTarea(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstatusTareaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstatusTareaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstatusTareaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estatusTarea).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
