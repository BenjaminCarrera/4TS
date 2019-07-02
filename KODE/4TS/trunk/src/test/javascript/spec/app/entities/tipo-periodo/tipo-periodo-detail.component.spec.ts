/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { TipoPeriodoDetailComponent } from 'app/entities/tipo-periodo/tipo-periodo-detail.component';
import { TipoPeriodo } from 'app/shared/model/tipo-periodo.model';

describe('Component Tests', () => {
  describe('TipoPeriodo Management Detail Component', () => {
    let comp: TipoPeriodoDetailComponent;
    let fixture: ComponentFixture<TipoPeriodoDetailComponent>;
    const route = ({ data: of({ tipoPeriodo: new TipoPeriodo(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TipoPeriodoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipoPeriodoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipoPeriodoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipoPeriodo).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
