/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { SkillRequerimientoDetailComponent } from 'app/entities/skill-requerimiento/skill-requerimiento-detail.component';
import { SkillRequerimiento } from 'app/shared/model/skill-requerimiento.model';

describe('Component Tests', () => {
  describe('SkillRequerimiento Management Detail Component', () => {
    let comp: SkillRequerimientoDetailComponent;
    let fixture: ComponentFixture<SkillRequerimientoDetailComponent>;
    const route = ({ data: of({ skillRequerimiento: new SkillRequerimiento(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [SkillRequerimientoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SkillRequerimientoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SkillRequerimientoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.skillRequerimiento).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
